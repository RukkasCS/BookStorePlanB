package dsa.bookstore;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;


public class BookDAO
{
    Connection conn = null;
    Statement stmnt = null;
    
    BookBST bookTree;
    
    public BookDAO()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/bookstore", "root", "");
            
            if (conn != null)
            {
                System.out.println("<<<<<<<<<<<<<<MySQL Connection Succeed!>>>>>>>>>>>>>>>>");
                stmnt = conn.createStatement();
            }
            else
            {
                System.err.println("<<<<<<<<<<<<<<MySQL Connection Failed!!>>>>>>>>>>>>>>>>");
            }
        }
        catch(Exception e)
        {
            System.err.println("Error occured in Connection or Statement!\nException Message: "+e.getMessage());
        }
    }
    
    public void regainConAndStmnt()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/bookstore", "root", "");
            
            if (conn != null)
            {
                System.out.println("<<<<<<<<<<<<<<MySQL Connection Succeed!>>>>>>>>>>>>>>>>");
                stmnt = conn.createStatement();
            }
            else
            {
//                System.err.println("<<<<<<<<<<<<<<MySQL Connection Failed!!>>>>>>>>>>>>>>>>");
            }
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Cannot find the DataBase!\n"+e.getMessage(), "Where is the DataBase?", 0);
        }
    }
    
    void insertBook(Book newBook)
    {
        String insertQuery = "INSERT INTO `bookstore`.`book` "
                + "(`Book_Title`, `ISBN`, `Author_FirstName`, `Author_Surname`) "
                + "VALUES ('"+newBook.getBookTitle()+"', '"+newBook.getISBN()+"',"
                + " '"+newBook.getAuthorFirstName()+"', '"+newBook.getAuthorSurname()+"');";
        try 
        {
            int result;
            result = stmnt.executeUpdate(insertQuery);
            
            if (result != 0)
            {
                JOptionPane.showMessageDialog(null, "Insertion Succeed!", "Good", 1);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "Insertion Failed!", "Bad", 0);
            }
        }
        catch (SQLException ex)
        {
            System.err.println("Error in execution of the Insert Query! \nException Message: "+ex.getMessage());
        }
        
        finally
        {
            if (conn != null)
            {
                try 
                {
                    conn.close();
                }
                catch (SQLException ex)
                {
                    System.err.println("Error in closing the Connection!");
                }
            }
            
            if (stmnt != null)
            {
                try 
                {
                    stmnt.close();
                } 
                catch (SQLException ex)
                {
                    System.err.println("Error in closing the Statement!");
                }
            }
        }
    }

    public ArrayList<Book> getBooks() 
    {
        ArrayList<Book> unorderedList = new ArrayList<Book>();
        String selectQuery = "SELECT * FROM bookstore.book";
        ResultSet resultSet = null;
        try 
        {
            resultSet = stmnt.executeQuery(selectQuery);
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Unsuccessfull!\n"+ ex.getMessage());
        }
        
        try 
        {
            while(resultSet.next())
            {
                int b_No = resultSet.getInt("Book_No");
                String b_Title = resultSet.getString("Book_Title");
                int b_ISBN = resultSet.getInt("ISBN");
                String b_Au_FN = resultSet.getString("Author_FirstName");
                String b_Au_SN = resultSet.getString("Author_Surname");
                
                Book book = new Book(b_No, b_Title, b_ISBN, b_Au_FN, b_Au_SN);
                unorderedList.add(book);
            }
        } 
        catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, "Unsuccessfull!\n"+ ex.getMessage());
        }
        
        finally
        {
            if (conn != null)
            {
                try 
                {
                    conn.close();
                }
                catch (SQLException ex)
                {
                    JOptionPane.showMessageDialog(null, "Unsuccessfull!\n"+ ex.getMessage());
                }
            }
            
            if (stmnt != null)
            {
                try 
                {
                    stmnt.close();
                } 
                catch (SQLException ex)
                {
                    JOptionPane.showMessageDialog(null, "Unsuccessfull!\n"+ ex.getMessage());
                }
            }
        }
        
        //Sending the unorderedList to BST and make it orderedList
        BookBST tree = new BookBST();
        
        for(Book book: unorderedList)
            {
                tree.addNode_keyBookTitle(book);
            }
             
        ArrayList<BookNode> orderedList1 = tree.inOrderTraverseTree(tree.root);
        ArrayList<Book> orderedList = new ArrayList<Book>();
        for (BookNode node: orderedList1)
        {
            Book book = new Book();
            
            book.setBookNo(node.b_No);
            book.setBookTitle(node.b_Title);
            book.setISBN(node.isbn);
            book.setAuthorFirstName(node.au_FN);
            book.setAuthorSurname(node.au_SN);
            
            orderedList.add(book);
        }
//        ArrayList<Book> orderedList = (ArrayList)orderedList1;
        return orderedList;
    }
    
    public ArrayList<Book> getExistingTree()
    {
        ArrayList<BookNode> list = new ArrayList<BookNode>();
        ArrayList<Book> list2 = new ArrayList<Book>();
        try
        {
            list = bookTree.inOrderTraverseTree(bookTree.root);
            for (BookNode node:list)
            {
                Book book = new Book(node);
                list2.add(book);
            }
        }
        catch(NullPointerException e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return list2;
    }
    
    public Book getBook(int isbn)
    {
        Book result;
        ArrayList<Book> allBooks = this.getBooks();
        bookTree = new BookBST();
        
        for(Book b:allBooks)
        {
            bookTree.addNode_keyISBN(b);
        }
        
        BookNode node = bookTree.findNode_keyISBN(isbn);
        
        if (node != null)
        {
            result = new Book(node);
        }
        else
        {
            result = null;
        }
        
        return result;
    }
    
    public Book getBook(String title)
    {
        Book result;
        ArrayList<Book> allBooks = this.getBooks();
        bookTree = new BookBST();
        
        for(Book b:allBooks)
        {
            bookTree.addNode_keyBookTitle(b);
        }
        
        BookNode node = bookTree.findNode_keyBookTitle(title);
        
        if (node != null)
        {
            result = new Book(node);
        }
        else
        {
            result = null;
        }
        
        return result;
    }
    
    public ArrayList<BookNode> removeBook(Book book, int treeType)
    {
        boolean result1 = false;
        boolean result2 = false;
        boolean fResult = false;
        
        ArrayList<BookNode> newList = new ArrayList<BookNode>();
                
        String deleteQuery = "DELETE FROM `book` WHERE `Book_No`= "+book.getBookNo()+"";
         
        try 
        {
            int res = stmnt.executeUpdate(deleteQuery);
            if (res != 0)
            {
                result1 = true;
                JOptionPane.showMessageDialog(null, "Deleted from the DataBase!", "Good", 1);
                
                //Remove from tree
                if (treeType == 1)
                {
                    result2 = bookTree.removeNode_keyISBN(book);
                    bookTree.refresh();
                    newList = bookTree.inOrderTraverseTree(bookTree.root);
                }
                else
                {
                    result2 = bookTree.removeNode_keyBookTitle(book);
                    bookTree.refresh();
                    newList = bookTree.inOrderTraverseTree(bookTree.root);
                }
                if (!result2)
                {
                    JOptionPane.showMessageDialog(null, "Deleting from the BST Failed!", "Bad", 0);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Deleted from the BST!", "Good", 1);
                }
            }
            else
            {
                result1 = false;
                JOptionPane.showMessageDialog(null, "Deleting from the DataBase Failed!", "Bad", 0);
            }
        }
        catch (SQLException ex)
        {
            System.err.println("Error in execution of the Delete Query! \n "+ex.getMessage()+"");
        }

        finally
        {
            if (conn != null)
            {
                try 
                {
                    conn.close();
                }
                catch (SQLException ex)
                {
                    System.err.println("Error in closing the Connection!");
                }
            }

            if (stmnt != null)
            {
                try 
                {
                    stmnt.close();
                } 
                catch (SQLException ex)
                {
                    System.err.println("Error in closing the Statement!");
                }
            }
        }
        
        if (result1 && result2)
        {
            fResult = true;
        }
        
        if (fResult)
        {
            JOptionPane.showMessageDialog(null, "Book Removal Succeed!");
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Book Removal Unsuccessfull!");
        }
        
        return newList;
    }
    
    public ArrayList<Book> getBooksFromKeyWord(String kw)
    {
        ArrayList<Book> unorderedList = new ArrayList<Book>();
        String selectQuery = "SELECT * FROM `book` WHERE `Book_Title` LIKE '%"+kw+"%'";
        ResultSet resultSet = null;
        try 
        {
            resultSet = stmnt.executeQuery(selectQuery);
        }
        catch (SQLException ex)
        {
            JOptionPane.showMessageDialog(null, "Unsuccessfull!\n"+ ex.getMessage());
        }
        
        try 
        {
            while(resultSet.next())
            {
                int b_No = resultSet.getInt("Book_No");
                String b_Title = resultSet.getString("Book_Title");
                int b_ISBN = resultSet.getInt("ISBN");
                String b_Au_FN = resultSet.getString("Author_FirstName");
                String b_Au_SN = resultSet.getString("Author_Surname");
                
                Book book = new Book(b_No, b_Title, b_ISBN, b_Au_FN, b_Au_SN);
                unorderedList.add(book);
            }
        } 
        catch (SQLException ex) 
        {
            JOptionPane.showMessageDialog(null, "Unsuccessfull!\n"+ ex.getMessage());
        }
        
        finally
        {
            if (conn != null)
            {
                try 
                {
                    conn.close();
                }
                catch (SQLException ex)
                {
                    JOptionPane.showMessageDialog(null, "Unsuccessfull!\n"+ ex.getMessage());
                }
            }
            
            if (stmnt != null)
            {
                try 
                {
                    stmnt.close();
                } 
                catch (SQLException ex)
                {
                    JOptionPane.showMessageDialog(null, "Unsuccessfull!\n"+ ex.getMessage());
                }
            }
        }
        
        //Sending the unorderedList to BST and make it orderedList
        BookBST tree = new BookBST();
        
        for(Book book: unorderedList)
            {
                tree.addNode_keyBookTitle(book);
            }
             
        ArrayList<BookNode> orderedList1 = tree.inOrderTraverseTree(tree.root);
        ArrayList<Book> orderedList = new ArrayList<Book>();
        for (BookNode node: orderedList1)
        {
            Book book = new Book();
            
            book.setBookNo(node.b_No);
            book.setBookTitle(node.b_Title);
            book.setISBN(node.isbn);
            book.setAuthorFirstName(node.au_FN);
            book.setAuthorSurname(node.au_SN);
            
            orderedList.add(book);
        }
//        ArrayList<Book> orderedList = (ArrayList)orderedList1;
        return orderedList;
    }
    
}
