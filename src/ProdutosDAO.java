import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ProdutosDAO {
    
    Connection conn = new conectaDAO().connectDB();
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public int cadastrarProduto (ProdutosDTO produto){
         int status;
        try {
            prep = conn.prepareStatement("INSERT INTO produtos VALUES(?,?,?,?)");
            prep.setInt(1, 0);
            prep.setString(2, produto.getNome());
            prep.setInt(3, produto.getValor());
            prep.setString(4, produto.getStatus());
            status = prep.executeUpdate();
            if (status == 1) {
                JOptionPane.showMessageDialog(null, "Produto incluidos com sucesso");
            } else if (status == 1062) {
                JOptionPane.showMessageDialog(null, "Produto já foi cadastrada");
            } else {
                JOptionPane.showMessageDialog(null, "Erro ao tentar inserir dados");
            }
            return status; 
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return ex.getErrorCode();
        }
        
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(String Filtro){
         String sql = "select * from uc11.produtos Where status like 'A Venda'";

        if (!Filtro.isEmpty()) {
            sql = sql + " Where id like ? ";
        }

        try {
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            while (resultset.next()) {
                ProdutosDTO pr = new ProdutosDTO();
                pr.setId(resultset.getInt("id"));
                pr.setNome(resultset.getString("nome"));
                pr.setValor(resultset.getInt("valor"));
                pr.setStatus(resultset.getString("status"));
                listagem.add(pr);
            }
            return listagem;

        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }
    }
    
     public List<ProdutosDTO> listarProdutosVendidos(String Filtro) {

        String sql = "select * from uc11.produtos Where status like 'Vendido'";

        try {
            prep = conn.prepareStatement(sql);

            resultset = prep.executeQuery();

            while (resultset.next()) {
                ProdutosDTO pr = new ProdutosDTO();
                pr.setId(resultset.getInt("id"));
                pr.setNome(resultset.getString("nome"));
                pr.setValor(resultset.getInt("valor"));
                pr.setStatus(resultset.getString("status"));
                listagem.add(pr);
            }
            return listagem;

        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }

    }
    
     public int venderProduto(ProdutosDTO pr) {
        int status;
        try {
            prep = conn.prepareStatement("UPDATE produtos SET id = ?, nome =?, valor =?,status = ? where id =?");
            prep.setInt(1, pr.getId());
            prep.setString(2, pr.getNome());
            prep.setInt(3, pr.getValor());
            prep.setString(4, "Vendido");
            prep.setInt(5, pr.getId());
            status = prep.executeUpdate();
            JOptionPane.showMessageDialog(null, "Produto Vendido");
            return status; //retornar 1
        } catch (SQLException ex) {
            System.out.println(ex.getErrorCode());
            return ex.getErrorCode();
        }
    }
     
     public ProdutosDTO consultar(int id) {

        try {
            ProdutosDTO pr = new ProdutosDTO();
            prep = conn.prepareStatement("SELECT * from produtos WHERE id = ?");
            prep.setInt(1, id);
            resultset = prep.executeQuery();
            //verificar se a consulta encontrou o funcionário com a matrícula informada
            if (resultset.next()) { // se encontrou o funcionário, vamos carregar os dados
                pr.setId(resultset.getInt("id"));
                pr.setNome(resultset.getString("nome"));
                pr.setValor(resultset.getInt("valor"));
                pr.setStatus(resultset.getString("status"));
                return pr;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }
    }
    
    
    
        
}

