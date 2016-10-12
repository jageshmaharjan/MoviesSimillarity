
package com.thu.simillarity.IMDBJavaObjectCode;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.thu.simillarity.IMDBJavaObjectCode package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.thu.simillarity.IMDBJavaObjectCode
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ImdbCom }
     * 
     */
    public ImdbCom createImdbCom() {
        return new ImdbCom();
    }

    /**
     * Create an instance of {@link ImdbCom.IMDB }
     * 
     */
    public ImdbCom.IMDB createImdbComIMDB() {
        return new ImdbCom.IMDB();
    }

    /**
     * Create an instance of {@link ImdbCom.IMDB.Genres }
     * 
     */
    public ImdbCom.IMDB.Genres createImdbComIMDBGenres() {
        return new ImdbCom.IMDB.Genres();
    }

    /**
     * Create an instance of {@link ImdbCom.IMDB.Actors }
     * 
     */
    public ImdbCom.IMDB.Actors createImdbComIMDBActors() {
        return new ImdbCom.IMDB.Actors();
    }

    /**
     * Create an instance of {@link ImdbCom.IMDB.Drectors }
     * 
     */
    public ImdbCom.IMDB.Drectors createImdbComIMDBDrectors() {
        return new ImdbCom.IMDB.Drectors();
    }

}
