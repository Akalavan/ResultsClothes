package app.objects;

public class Checklist {

    /*
      id_checklist INT IDENTITY NOT NULL,
  id_position INT NOT NULL,
  id_personal_protective INT NOT NULL,
  quantity_year VARCHAR(25) NOT NULL,
  id_normal_document INT NOT NULL,
  id_add_note INT,
     */

    private int id;
    private int idPosition;
    private int idPersonal;
    private String quantityYear;
    private int idNormalDocument;
    private int idAddNote;

    public Checklist(int id, int idPosition, int idPersonal, String quantityYear, int idNormalDocument, int idAddNote) {
        this.id = id;
        this.idPosition = idPosition;
        this.idPersonal = idPersonal;
        this.quantityYear = quantityYear;
        this.idNormalDocument = idNormalDocument;
        this.idAddNote = idAddNote;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPosition() {
        return idPosition;
    }

    public void setIdPosition(int idPosition) {
        this.idPosition = idPosition;
    }

    public int getIdPersonal() {
        return idPersonal;
    }

    public void setIdPersonal(int idPersonal) {
        this.idPersonal = idPersonal;
    }

    public String getQuantityYear() {
        return quantityYear;
    }

    public void setQuantityYear(String quantityYear) {
        this.quantityYear = quantityYear;
    }

    public int getIdNormalDocument() {
        return idNormalDocument;
    }

    public void setIdNormalDocument(int idNormalDocument) {
        this.idNormalDocument = idNormalDocument;
    }

    public int getIdAddNote() {
        return idAddNote;
    }

    public void setIdAddNote(int idAddNote) {
        this.idAddNote = idAddNote;
    }
}
