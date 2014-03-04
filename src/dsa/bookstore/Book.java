package dsa.bookstore;

public class Book
{
    private int bookNo;
    private String bookTitle;
    private int isbn;
    private String author_FN;
    private String author_SN;
    
    //Getters and Setters
    public void setBookNo(int bNo)
    {
        this.bookNo = bNo;
    }
    
    public int getBookNo ()
    {
        return this.bookNo;
    }
    
    public void setBookTitle(String t)
    {
        this.bookTitle = t;
    }
    
    public String getBookTitle()
    {
        return this.bookTitle;
    }
    
    public void setISBN(int i)
    {
        this.isbn = i;
    }
    
    public int getISBN()
    {
        return this.isbn;
    }
    
    public void setAuthorFirstName(String aFN)
    {
        this.author_FN = aFN;
    }
    
    public String getAuthorFirstName()
    {
        return this.author_FN;
    }
    
    public void setAuthorSurname(String aSN)
    {
        this.author_SN = aSN;
    }
    
    public String getAuthorSurname()
    {
        return this.author_SN;
    }
    
    //Default Constructor
    public Book()
    {        
         this.bookNo = 0;
         this.bookTitle = null;
         this.isbn = 0;
         this.author_FN = null;
         this.author_SN = null;
    }
    
    //Parameterized Constructor 01
    public Book(String t, int i, String aFN, String aSN)
    {
        this.bookTitle = t;
        this.isbn = i;
        this.author_FN = aFN;
        this.author_SN = aSN;
    }
    
    //Parameterized Constructor 02
    public Book(int bNo, String t, int i, String aFN, String aSN)
    {
        this.bookNo = bNo;
        this.bookTitle = t;
        this.isbn = i;
        this.author_FN = aFN;
        this.author_SN = aSN;
    }
    
    //Parameterized Constructor 03
    public Book(BookNode node)
    {
        this.bookNo = node.b_No;
        this.bookTitle = node.b_Title;
        this.isbn = node.isbn;
        this.author_FN = node.au_FN;
        this.author_SN = node.au_SN;
    }
}
