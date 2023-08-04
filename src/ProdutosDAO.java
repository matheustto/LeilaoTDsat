import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


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
    
    public ArrayList<ProdutosDTO> listarProdutos(){
         String sql = "select * from uc11.produtos";


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
    
    
    
        
}

