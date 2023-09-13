import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaInsercaoComParametro {
    public static void main(String[] args) throws SQLException {
        ConnectionFactory factory = new ConnectionFactory();
        try (Connection connection = factory.recuperarConexao()){

            connection.setAutoCommit(false);

            try (PreparedStatement stm = connection.prepareStatement("INSERT INTO PRODUTO (nome, descricao) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {            
                getRst("SMART TV", "SAMSUNG - 45 POLEGADAS", stm);
                getRst("RADIO", "RADIO DE BATERIA CCE", stm);

                connection.commit();           
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("O Rollback foi executado.");
                connection.rollback();
            }
        }
    }

    private static void getRst(String nome, String descricao, PreparedStatement stm) throws SQLException {
        stm.setString(1, nome);
        stm.setString(2, descricao);
        stm.execute();

        try (ResultSet rst = stm.getGeneratedKeys()) {
            while(rst.next()) {
                Integer id = rst.getInt(1);
                System.out.println("O id craido foi: " + id);
            }
        }
    }
}
