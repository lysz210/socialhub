/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unive.taw.sun.lingyong.socialhub.models;

import it.unive.taw.sun.lingyong.socialhub.service.HistoryEntry;
import it.unive.taw.sun.lingyong.socialhub.service.Utente;
import it.unive.taw.sun.lingyong.socialhub.service.interfacce.HistoryEntryBean;
import it.unive.taw.sun.lingyong.socialhub.service.servlets.ProtocolConstantInterface;
import it.unive.taw.sun.lingyong.socialhub.service.servlets.Query;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;
import java.util.StringJoiner;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ciao
 */
public class DBHelper {
    public static final String JDBC_DERBY = "org.apache.derby.jdbc.EmbeddedDriver";
    public static final String PROTOCOL_DERBY = "jdbc:derby:";
    public static final String DB_NAME =  "SocialHubData";
    private static Connection _connection = null;
    
    public static final String ID_UTENTE_PTN = "%s@%s";
    
    public DBHelper() {
        //empty constructor -- helper class
    }
    
    public Connection getDerbyCon() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        if(_connection != null && !_connection.isClosed()) {
            return _connection;
        }
        Connection con = null;
        Class.forName(JDBC_DERBY).newInstance();
        String dns = PROTOCOL_DERBY + DB_NAME;
        try {
            con = DriverManager.getConnection(dns);
            System.out.println("DB oppened.");
        } catch (SQLException e) {
            if(dbConnectionFailed(e)){
                System.out.println(e.getMessage());
                con = DriverManager.getConnection(dns + ";create=true") ;
                System.out.println("DB created.");
            }
        }
        _connection = con;
        return con;
    }
    
    public void closeDerbyCon()throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        Connection con = null;
        Class.forName(JDBC_DERBY).newInstance();
        DriverManager.getConnection(PROTOCOL_DERBY + ";shutdown=true") ;
    }
    
    public static void createAllSocialHubTables(Connection con) throws SQLException{
        System.out.println("init tables");
        String[] qs = {
            Servizi.getCreateTable(),
            Utenti.getCreateTable(),
            History.getCreateTable(),
            HistoryServices.getCreateTable()
        };
        Statement stm = con.createStatement();
        for(int i = 0, len = qs.length; i < len; i++){
            String q = qs[i];
            System.out.println("Executing try: " + q);
            try{
                stm.execute(q);
            } catch(SQLException e) {
                // se l'errore e' dovuto all'esistenza della tabella continua
                if(!tableAlreadyExists(e)) {
                    System.out.println(e.getMessage());
                    throw e;
                }
                System.out.println("table already exists");
            }
        }
    }
    
    public static boolean dbConnectionFailed(SQLException e) {
        boolean conFailed;
        if(e.getSQLState().equals("08004")) {
            conFailed = true;
        } else {
            conFailed = true;
        }
        return conFailed;
    }

    public static boolean tableAlreadyExists(SQLException e) {
        boolean exists;
        if(e.getSQLState().equals("X0Y32")) {
            exists = true;
        } else {
            exists = false;
        }
        return exists;
    }
    
    public int insertUtente(Utente utente) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        int response = 0;
        if(utente == null){
            return response;
        }
        String insertQ = createInsertQuery(Utenti.TABLE_NAME, Utenti.values());
        String selectQ = String.format("SELECT * FROM %s WHERE %s = ?", Utenti.TABLE_NAME, Utenti.id_utente);
        String idUtente = getIdUtente(utente);
        Connection con = new DBHelper().getDerbyCon();
        PreparedStatement statement;
        statement = con.prepareStatement(selectQ);
        statement.setString(1, idUtente);
        ResultSet res = statement.executeQuery();
        if(!res.next()) {
            statement = con.prepareStatement(insertQ);
            statement.setString(1, idUtente);
            response = statement.executeUpdate();
        } else {
            response = 1;
        }
        return response;
    }
    
    public static String createInsertQuery(String tname, DBTableInterface[] cols){
        StringJoiner cNames = new StringJoiner(", ");
        StringJoiner vPads = new StringJoiner(", ");
        for(DBTableInterface c: cols){
            cNames.add(c.name());
            vPads.add("?");
        }
        String insertQ = String.format("INSERT INTO %s (%s) VALUES (%s)", tname, cNames.toString(), vPads.toString());
        return insertQ;
    }
    
    public int insertServices(Set<String> services) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        int result = 0;
        String insertQ = createInsertQuery(Servizi.TABLE_NAME, Servizi.values());
        Connection con = getDerbyCon();
        PreparedStatement statement = con.prepareStatement(insertQ);
        for(String service: services){
            statement.setString(1, service);
            try{
                int count = statement.executeUpdate();
                result += count;
            } catch (SQLException e){
                
            }
        }
        return result;
    }
    
    public it.unive.taw.sun.lingyong.socialhub.service.History getHistory(Utente user) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        it.unive.taw.sun.lingyong.socialhub.service.History history = new it.unive.taw.sun.lingyong.socialhub.service.History();
        Connection con = getDerbyCon();
        String idUtente = getIdUtente(user);
        String selectHistory = String.format(
                "SELECT H.%s, H.%s, H.%s, S.%s FROM %s H JOIN %s S ON H.%s = S.%s WHERE %s = ? ORDER BY H.%s DESC",
                History.id_history, History.parole, History.data, HistoryServices.service,
                History.TABLE_NAME, HistoryServices.TABLE_NAME,
                History.id_history, HistoryServices.history,
                History.utente,
                History.id_history
        );
        String selectService = String.format(
                "SELECT %s FROM %s WHERE %s = ?", 
                HistoryServices.service,
                HistoryServices.TABLE_NAME,
                HistoryServices.history
        );
        PreparedStatement statement = con.prepareStatement(selectHistory);
        statement.setMaxRows(ProtocolConstantInterface.HISTORY_LIMIT);
        statement.setString(1, idUtente);
        
        ResultSet historyResult = statement.executeQuery();
        
        Map<Long, HistoryEntryBean> historyEntries = new HashMap<>();
        
        while(historyResult.next()){
            Long id = historyResult.getLong(History.id_history.name());
            String servizio = historyResult.getString(HistoryServices.service.name());
            HistoryEntryBean _historyEntry = historyEntries.get(id);
            if(_historyEntry == null) {
                _historyEntry = new HistoryEntry();
                String parole = historyResult.getString(History.parole.name());
                Date data = historyResult.getDate(History.data.name());
                Long millis = data.getTime();
                _historyEntry.setData(new Timestamp(millis));
                _historyEntry.setIdHistory(id);
                _historyEntry.setParole(Arrays.asList(parole.split(",")));
                historyEntries.put(id, _historyEntry);
            }
            _historyEntry.addServizio(servizio);
        }
        
        history.setRecords(historyEntries.values());
        
        return history;
    }
    
    public static String getIdUtente(Utente utente){
        return String.format(ID_UTENTE_PTN, utente.getIdUtente(), "twitter");
    }
    
    public void registerHistoryEntry(Query query, Utente utente) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException{
        Connection con = getDerbyCon();
        String historyInsertQ = String.format(
            "INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)",
            History.TABLE_NAME,
            History.utente, History.parole, History.data
        );
        String hsInsertQ = String.format(
            "INSERT INTO %s (%s, %s) VALUES (?, ?)",
            HistoryServices.TABLE_NAME,
            HistoryServices.history, HistoryServices.service
        );
        PreparedStatement statement = con.prepareStatement(historyInsertQ, Statement.RETURN_GENERATED_KEYS);
        
        String idUtente = getIdUtente(utente);
        String parole = String.join(",", query.getTags());
        Date data = new Date(new java.util.Date().getTime());
        
        statement.setString(1, idUtente);
        statement.setString(2, parole);
        statement.setDate(3, data);
        
        int count = statement.executeUpdate();
        
        if(count > 0){
            ResultSet rs = statement.getGeneratedKeys();
            if(rs.next()){
                int historyId = rs.getInt(1);
                statement = con.prepareStatement(hsInsertQ);
                for(String service: query.getServices()){
                    statement.setLong(1, historyId);
                    statement.setString(2, service);
                    try{
                        statement.executeUpdate();
                    } catch (SQLException e) {
                        
                    }
                }
            }
        }
    }
}
