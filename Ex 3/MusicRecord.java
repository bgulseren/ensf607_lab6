import java.io.Serializable;

/**
 *   A simple class that represents a music record
 *
 */
class MusicRecord implements Serializable{
		
		//explicitly declare serialVersionID
		static final long serialVersionUID = 1L;
	
		private int year_recorded;
		private String songName;
		private String singerName;
		private double purchase_price;
		
        /**
         * A default constructor that builds a record with blank data
         */
		public MusicRecord() {
			this( 0, "", "", 0.0 ); 
		} 
	  
        /**
         * A constructor that initializes the music records with supplied 
         * data.
         * 
         * @param year - year song was written
         * @param song - name of song
         * @param singer - name of singer
         * @param value - price of record
         */
		public MusicRecord( int year, String song, String singer, double value ) {
			setYear( year );
			setSongName( song );
			setSingerName( singer );
			setPrice( value );
		} 
		
        
        /**
         * Sets the data field year_recorded to supplied argument year data.
         * 
         * @param year - year song written
         */
		public void setYear( int year ) {
			year_recorded = year;
		}
        
        
        /**
         * Returns the recording year
         */
		public int getYear() {
			return year_recorded; 
		}
        
        /**
         * Sets the data field songName to supplied argument song
         * 
         * @param song - name of song
         */
		public void setSongName( String song ) {
			songName = song;
		}
        
        
        /**
         * Returns the songName name
         */
		public String getSongName()  {
			return songName; 
		} 
		
        /**
         * Sets the data field sinterName to supplied argument singer
         * 
         * @param singer - name of singer
         */
		public void setSingerName( String singer ) {
			singerName = singer;
		}
        
        /**
         * Returns the singer's name
         */
		public String getSingerName() {
			return singerName; 
		}
        
        /**
         * Sets the data field purchase_price to supplied argument price
         * 
         * @param value - price of song/record
         */
		public void setPrice( double value ) {
			purchase_price = value;
		}
        
        /**
         * Returns the price
         */
		public double getPurchasePrice(){
			return purchase_price; 
		} 
	} 
