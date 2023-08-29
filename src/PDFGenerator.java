import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;

public class PDFGenerator {
    public static void main(String[] args) {
        // Crear el documento
        Document document = new Document();

        try {
            // Especificar la ruta de salida del PDF
            FileOutputStream outputStream = new FileOutputStream("formulario.pdf");

            // Inicializar el escritor PDF
            PdfWriter.getInstance(document, outputStream);

            // Abrir el documento
            document.open();

            // Agregar contenido al PDF
            // Aquí puedes agregar tu formulario y otros elementos

            // Ejemplo: Agregar un título
            Paragraph title = new Paragraph("Formulario");
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Cerrar el documento
            document.close();
            outputStream.close();

            System.out.println("PDF generado exitosamente.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
