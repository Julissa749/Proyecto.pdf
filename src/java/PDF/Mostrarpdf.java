package PDF;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = ("/pdf"))
public class Mostrarpdf extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/pdf");
        OutputStream salida = response.getOutputStream();

        try {
            
             try{
             Connection con = null;
             Statement st = null;
             ResultSet rs = null;
             Class.forName("com.mysql.jdbc.Driver");
             con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/bd_recurso_humano?zeroDateTimeBehavior=convertToNull", "root", "");
             st = (Statement) con.createStatement();
             rs = st.executeQuery("Select*from tb_persona");
             if(con!=null){
                 try {
                Document Documento = new Document();
                PdfWriter.getInstance(Documento, salida);
                Documento.open();
                Documento.addAuthor("Julissa Esmeralda ");
                Documento.addCreator("Salinas Melendez");

                
                Paragraph titulo = new Paragraph();
                Paragraph parrafo = new Paragraph();
                Font font_titulo = new Font(Font.FontFamily.HELVETICA,16,Font.BOLD, BaseColor.BLACK);
                Font font_parrafos = new Font(Font.FontFamily.COURIER, 12,Font.NORMAL, BaseColor.RED);
                titulo.add(new Phrase("Base de datos en formato PDF",font_titulo));
                titulo.setAlignment(Element.ALIGN_CENTER);
                
                titulo.add(new Phrase(Chunk.NEWLINE));
                titulo.add(new Phrase(Chunk.NEWLINE));
                Documento.add(titulo);
                parrafo.setAlignment(Element.ALIGN_JUSTIFIED);
                parrafo.add(new Phrase(Chunk.NEWLINE));
                parrafo.add(new Phrase(Chunk.NEWLINE));
                parrafo.add(new Phrase(Chunk.NEWLINE));
                Documento.add(parrafo);
                
                PdfPTable tabla = new PdfPTable(3);
                PdfPCell celda1 = new PdfPCell(new Paragraph("dui_persona", FontFactory.getFont("Arial",12,BaseColor.BLUE)));
                PdfPCell celda2 = new PdfPCell(new Paragraph("apellido_persona", FontFactory.getFont("Arial",12,BaseColor.BLUE)));
                PdfPCell celda3 = new PdfPCell(new Paragraph("nombre_persona", FontFactory.getFont("Arial",12,BaseColor.BLUE)));
      
                celda1.setPaddingBottom(5);
                celda3.setPaddingBottom(5);
                tabla.addCell(celda1);
                tabla.addCell(celda2);
                tabla.addCell(celda3);
                
                while(rs.next()){
                 tabla.addCell(rs.getString(1));
                 tabla.addCell(rs.getString(2));
                 tabla.addCell(rs.getString(3));
                }
                Documento.add(tabla);
                Documento.close();
            } catch (DocumentException e) {
                System.out.println("Error vuelva a intentar mas tarde " + e);
            }
                 
             }
             }catch(Exception ex){ex.getMessage();}
             
        } finally{
            
        }
    }

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }

}