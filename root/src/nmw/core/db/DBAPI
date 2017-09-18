package nmw.db;

import java.io.File;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

import org.apache.ibatis.jdbc.ScriptRunner;

public abstract class DBAPI<E extends DBAPIParam> {

    private Connection conn = null;

    public void initialize(E parameter)
            throws NumberFormatException, JMSException, SQLException, ClassNotFoundException {

        createConnection(parameter);
    }

    protected void createConnection(E parameter) throws SQLException, ClassNotFoundException {

        conn = DriverManager.getConnection(getDatabaseUrl(parameter));
    }

    protected abstract String getDatabaseUrl(E parameter) throws ClassNotFoundException;

    public void closeConnection() {

        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void truncateTableData(String tableName) {

        String truncateQuery = "TRUNCATE TABLE ?";
        PreparedStatement pstmt;

        try {

            pstmt = conn.prepareStatement(truncateQuery);

            pstmt.setString(1, tableName);

            pstmt.executeUpdate(truncateQuery);

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    public List<Map<String, Object>> searchQuery(String query, List<?> parameters)
            throws SQLException, ClassNotFoundException {

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            pstmt = conn.prepareStatement(query);

            if (parameters != null) {

                for (int i = 1; i <= parameters.size(); i++)
                    pstmt.setObject(i, parameters.get(i - 1));
            }

            rs = pstmt.executeQuery();
            return extractMapFromResultSet(rs);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            rs.close();
            pstmt.close();
        }

    }

    private List<Map<String, Object>> extractMapFromResultSet(ResultSet rSet) throws SQLException {
        ResultSetMetaData resultSetMetaData = null;
        resultSetMetaData = rSet.getMetaData();

        List<Map<String, Object>> output = new ArrayList<Map<String, Object>>();

        int columnCount = resultSetMetaData.getColumnCount();

        while (rSet.next()) {

            Map<String, Object> row = new HashMap<String, Object>();

            for (int index = 1; index <= columnCount; index++)
                row.put(resultSetMetaData.getColumnName(index), rSet.getObject(resultSetMetaData.getColumnName(index)));

            output.add(row);
        }
        return output;

    }

    public List<Map<String, Object>> executeProcedure(String procedureCall, List<?> inputParameters,
            List<ProcedureOutParam> outputParameters) throws SQLException, ClassNotFoundException {

        CallableStatement cstmt = null;

        cstmt = conn.prepareCall(procedureCall);

        if (inputParameters != null)
            for (int i = 0; i < inputParameters.size(); i++)
                cstmt.setObject((i + 1), inputParameters.get(i));

        int inLength = inputParameters.size();

        if (outputParameters != null)
            for (int o = 0; o < outputParameters.size(); o++)
                cstmt.registerOutParameter((o + inLength + 1), outputParameters.get(o).getType());

        boolean isOutputResultSet = cstmt.execute();

        if (outputParameters != null)
            for (int o = 0; o < outputParameters.size(); o++)
                outputParameters.get(o).setValue(cstmt.getObject(o + inLength + 1));

        if (isOutputResultSet) {
            ResultSet resultSet = cstmt.getResultSet();
            return extractMapFromResultSet(resultSet);
        }

        return null;
    }

    public void executeScript(String path) throws Exception {

        String scriptPath = CommonUtils.getResourcePath(path);
        
        ScriptRunner runner = new ScriptRunner(conn);

        File sqlScript = new File(scriptPath);
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(System.out, "UTF-8"));

        runner.setErrorLogWriter(writer);
        runner.setStopOnError(true);
        runner.setLogWriter(writer);
        runner.setSendFullScript(true);
        runner.setDelimiter(";");
        runner.setAutoCommit(true);

        runner.runScript(new FileReader(sqlScript));
    }
}
