/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

/**
 *
 * @author Alex
 */
public class Piece {
    private String theaterPlayName;
    private int placesNumber; 
    
    public Piece(){
        System.out.println("Création d'une pièce");
        theaterPlayName="Inconnu";
        placesNumber=0;
    }
     public Piece(String Nom, int nbr)
  {
    System.out.println("Création d'une pièce");
    theaterPlayName = Nom;
    placesNumber = nbr;
  }  
     
     
    public int achatPlace(int i){
        placesNumber=placesNumber-i;
        return placesNumber;
    } 
     
     
     
     public String getNom()  {  
    return theaterPlayName;
  }

  public int getNombrePlace()
  {
    return placesNumber;
  } 

  public void setNom(String Nom)
  {
    theaterPlayName = Nom;
  }

  //Définit le nombre d'habitants
  public void setNombrePlace(int nbr)
  {
    placesNumber = nbr;
  }  
}

