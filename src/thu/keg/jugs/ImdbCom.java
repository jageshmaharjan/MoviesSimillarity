
package thu.keg.jugs;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IMDB" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="ReleaseDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Duration" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="StoryLine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Rating" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Genres" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Genre" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Actors" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Actor" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Drectors" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Director" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Reviews" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "imdb"
})
@XmlRootElement(name = "imdb.com")
public class ImdbCom {

    @XmlElement(name = "IMDB")
    protected List<ImdbCom.IMDB> imdb;

    /**
     * Gets the value of the imdb property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the imdb property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIMDB().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ImdbCom.IMDB }
     * 
     * 
     */
    public List<ImdbCom.IMDB> getIMDB() {
        if (imdb == null) {
            imdb = new ArrayList<ImdbCom.IMDB>();
        }
        return this.imdb;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="ReleaseDate" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Duration" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="StoryLine" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Rating" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Genres" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Genre" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Actors" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Actor" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Drectors" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Director" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Reviews" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *       &lt;/sequence>
     *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "title",
        "releaseDate",
        "duration",
        "storyLine",
        "rating",
        "genres",
        "actors",
        "drectors",
        "reviews"
    })
    public static class IMDB {

        @XmlElement(name = "Title")
        protected String title;
        @XmlElement(name = "ReleaseDate")
        protected String releaseDate;
        @XmlElement(name = "Duration")
        protected String duration;
        @XmlElement(name = "StoryLine")
        protected String storyLine;
        @XmlElement(name = "Rating")
        protected String rating;
        @XmlElement(name = "Genres")
        protected ImdbCom.IMDB.Genres genres;
        @XmlElement(name = "Actors")
        protected ImdbCom.IMDB.Actors actors;
        @XmlElement(name = "Drectors")
        protected ImdbCom.IMDB.Drectors drectors;
        @XmlElement(name = "Reviews")
        protected String reviews;
        @XmlAttribute(name = "id")
        protected String id;

        /**
         * Gets the value of the title property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTitle() {
            return title;
        }

        /**
         * Sets the value of the title property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTitle(String value) {
            this.title = value;
        }

        /**
         * Gets the value of the releaseDate property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getReleaseDate() {
            return releaseDate;
        }

        /**
         * Sets the value of the releaseDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setReleaseDate(String value) {
            this.releaseDate = value;
        }

        /**
         * Gets the value of the duration property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDuration() {
            return duration;
        }

        /**
         * Sets the value of the duration property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDuration(String value) {
            this.duration = value;
        }

        /**
         * Gets the value of the storyLine property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStoryLine() {
            return storyLine;
        }

        /**
         * Sets the value of the storyLine property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStoryLine(String value) {
            this.storyLine = value;
        }

        /**
         * Gets the value of the rating property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRating() {
            return rating;
        }

        /**
         * Sets the value of the rating property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRating(String value) {
            this.rating = value;
        }

        /**
         * Gets the value of the genres property.
         * 
         * @return
         *     possible object is
         *     {@link ImdbCom.IMDB.Genres }
         *     
         */
        public ImdbCom.IMDB.Genres getGenres() {
            return genres;
        }

        /**
         * Sets the value of the genres property.
         * 
         * @param value
         *     allowed object is
         *     {@link ImdbCom.IMDB.Genres }
         *     
         */
        public void setGenres(ImdbCom.IMDB.Genres value) {
            this.genres = value;
        }

        /**
         * Gets the value of the actors property.
         * 
         * @return
         *     possible object is
         *     {@link ImdbCom.IMDB.Actors }
         *     
         */
        public ImdbCom.IMDB.Actors getActors() {
            return actors;
        }

        /**
         * Sets the value of the actors property.
         * 
         * @param value
         *     allowed object is
         *     {@link ImdbCom.IMDB.Actors }
         *     
         */
        public void setActors(ImdbCom.IMDB.Actors value) {
            this.actors = value;
        }

        /**
         * Gets the value of the drectors property.
         * 
         * @return
         *     possible object is
         *     {@link ImdbCom.IMDB.Drectors }
         *     
         */
        public ImdbCom.IMDB.Drectors getDrectors() {
            return drectors;
        }

        /**
         * Sets the value of the drectors property.
         * 
         * @param value
         *     allowed object is
         *     {@link ImdbCom.IMDB.Drectors }
         *     
         */
        public void setDrectors(ImdbCom.IMDB.Drectors value) {
            this.drectors = value;
        }

        /**
         * Gets the value of the reviews property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getReviews() {
            return reviews;
        }

        /**
         * Sets the value of the reviews property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setReviews(String value) {
            this.reviews = value;
        }

        /**
         * Gets the value of the id property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getId() {
            return id;
        }

        /**
         * Sets the value of the id property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setId(String value) {
            this.id = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="Actor" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "actor"
        })
        public static class Actors {

            @XmlElement(name = "Actor")
            protected List<String> actor;

            /**
             * Gets the value of the actor property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the actor property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getActor().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link String }
             * 
             * 
             */
            public List<String> getActor() {
                if (actor == null) {
                    actor = new ArrayList<String>();
                }
                return this.actor;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="Director" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "director"
        })
        public static class Drectors {

            @XmlElement(name = "Director")
            protected List<String> director;

            /**
             * Gets the value of the director property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the director property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getDirector().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link String }
             * 
             * 
             */
            public List<String> getDirector() {
                if (director == null) {
                    director = new ArrayList<String>();
                }
                return this.director;
            }

        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="Genre" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "genre"
        })
        public static class Genres {

            @XmlElement(name = "Genre")
            protected List<String> genre;

            /**
             * Gets the value of the genre property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the genre property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getGenre().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link String }
             * 
             * 
             */
            public List<String> getGenre() {
                if (genre == null) {
                    genre = new ArrayList<String>();
                }
                return this.genre;
            }

        }

    }

}
