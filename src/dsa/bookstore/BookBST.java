package dsa.bookstore;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class BookBST 
{
    BookNode root;
    
    ArrayList<BookNode> orderedList = new ArrayList<BookNode>();
    
    public void refresh()
    {
        orderedList.clear();
    }
    
    public void addNode_keyISBN(Book newBook) 
    {
        // Create a new BookNode and initialize it
        BookNode newNode = new BookNode(newBook);

        // If there is no root this becomes root
        if (root == null) 
        {
            root = newNode;
        } 
        else 
        {
            // Set root as the BookNode we will start
            // with as we traverse the tree
            BookNode focusNode = root;

            // Future parent for our new BookNode
            BookNode parent;

            while (true) 
            {
                // root is the top parent so we start
                // there
                parent = focusNode;

                // Check if the new node should go on
                // the left side of the parent node
                if (newNode.isbn < focusNode.isbn) 
                {
                    // Switch focus to the left child
                    focusNode = focusNode.leftChild;

                    // If the left child has no children
                    if (focusNode == null) 
                    {
                        // then place the new node on the left of it
                        parent.leftChild = newNode;
                        return; // All Done
                    }
                }
                else 
                { 
                    // If we get here put the node on the right
                    focusNode = focusNode.rightChild;

                    // If the right child has no children
                    if (focusNode == null) 
                    {
                        // then place the new node on the right of it
                        parent.rightChild = newNode;
                        return; // All Done
                    }
                }
            }
        }
    }
    
    public void addNode_keyBookTitle(Book newBook) 
    {
        // Create a new BookNode and initialize it
        BookNode newNode = new BookNode(newBook);

        // If there is no root this becomes root
        if (root == null) 
        {
            root = newNode;
        } 
        else 
        {
            // Set root as the BookNode we will start
            // with as we traverse the tree
            BookNode focusNode = root;

            // Future parent for our new BookNode
            BookNode parent;

            while (true) 
            {
                // root is the top parent so we start
                // there
                parent = focusNode;

                // Check if the new node should go on
                // the left side of the parent node
                if (newNode.b_Title.compareTo(focusNode.b_Title)<0) 
                {
                    // Switch focus to the left child
                    focusNode = focusNode.leftChild;

                    // If the left child has no children
                    if (focusNode == null) 
                    {
                        // then place the new node on the left of it
                        parent.leftChild = newNode;
                        return; // All Done
                    }
                }
                else 
                { 
                    // If we get here put the node on the right
                    focusNode = focusNode.rightChild;

                    // If the right child has no children
                    if (focusNode == null) 
                    {
                        // then place the new node on the right of it
                        parent.rightChild = newNode;
                        return; // All Done
                    }
                }
            }
        }
    }
    
    public ArrayList<BookNode> inOrderTraverseTree(BookNode focusNode) 
    {
            if (focusNode != null) 
            {
                // Traverse the left node
                inOrderTraverseTree(focusNode.leftChild);

                // Visit the currently focused on node
                orderedList.add(focusNode);

                // Traverse the right node
                inOrderTraverseTree(focusNode.rightChild);
            }
            return orderedList;        
    }
    
    public void preorderTraverseTree(BookNode focusNode) 
    {
        if (focusNode != null) 
        {
            orderedList.add(focusNode);

            preorderTraverseTree(focusNode.leftChild);
            preorderTraverseTree(focusNode.rightChild);
        }
    }

    public void postOrderTraverseTree(BookNode focusNode) 
    {
        if (focusNode != null) 
        {
            postOrderTraverseTree(focusNode.leftChild);
            postOrderTraverseTree(focusNode.rightChild);

            orderedList.add(focusNode);
        }
    }

    public BookNode findNode_keyISBN(int isbn) 
    {
        // Start at the top of the tree
        BookNode focusNode = root;

        // While we haven't found the BookNode
        // keep looking
        while (focusNode.isbn != isbn) 
        {
            // If we should search to the left
            if (isbn < focusNode.isbn) 
            {
                // Shift the focus BookNode to the left child
                focusNode = focusNode.leftChild;
            } 
            else 
            {
                // Shift the focus BookNode to the right child
                focusNode = focusNode.rightChild;
            }

            // The node wasn't found
            if (focusNode == null)
            {
                return null;
            }
        }
        return focusNode;
    }
    
    public BookNode findNode_keyBookTitle(String b_Title) 
    {
        // Start at the top of the tree
        BookNode focusNode = root;

        // While we haven't found the BookNode
        // keep looking
        while (!focusNode.b_Title.equals(b_Title)) 
        {
            // If we should search to the left
            if (b_Title.compareTo(focusNode.b_Title)<0) 
            {
                // Shift the focus BookNode to the left child
                focusNode = focusNode.leftChild;
            } 
            else 
            {
                // Shift the focus BookNode to the right child
                focusNode = focusNode.rightChild;
            }
            
            // The node wasn't found
            if (focusNode == null)
            {
                return null;
            }
        }
        return focusNode;
    }
    
    public boolean removeNode_keyISBN(Book node) 
    {	 
        // Start at the top of the tree

        BookNode focusNode = root;
        BookNode parent = root;

        // When searching for a Node this will
        // tell us whether to search to the
        // right or left

        boolean isItALeftChild = true;

        // While we haven't found the Node
        // keep looking
        
//        JOptionPane.showMessageDialog(null, "ISBN: "+node.getISBN());
        
        while (focusNode.isbn != node.getISBN()) 
        {

            parent = focusNode;

            // If we should search to the left

            if (node.getISBN() < focusNode.isbn) 
            {

                isItALeftChild = true;

                // Shift the focus Node to the left child

                focusNode = focusNode.leftChild;

            } 
            else 
            {

                // Greater than focus node so go to the right

                isItALeftChild = false;

                // Shift the focus Node to the right child

                focusNode = focusNode.rightChild;

            }

            // The node wasn't found

            if (focusNode == null)
                return false;

        }

        // If Node doesn't have children delete it

        if (focusNode.leftChild == null && focusNode.rightChild == null) 
        {

            // If root delete it

        if (focusNode == root)
                root = null;

            // If it was marked as a left child
            // of the parent delete it in its parent

            else if (isItALeftChild)
                parent.leftChild = null;

            // Vice versa for the right child

            else
                parent.rightChild = null;

        }

        // If no right child

        else if (focusNode.rightChild == null) 
        {

            if (focusNode == root)
                root = focusNode.leftChild;

            // If focus Node was on the left of parent
            // move the focus Nodes left child up to the
            // parent node

            else if (isItALeftChild)
                parent.leftChild = focusNode.leftChild;

            // Vice versa for the right child

            else
                parent.rightChild = focusNode.leftChild;

        }

        // If no left child

        else if (focusNode.leftChild == null) 
        {

            if (focusNode == root)
                root = focusNode.rightChild;

            // If focus Node was on the left of parent
            // move the focus Nodes right child up to the
            // parent node

            else if (isItALeftChild)
                parent.leftChild = focusNode.rightChild;

            // Vice versa for the left child

            else
                parent.rightChild = focusNode.rightChild;

        }

        // Two children so I need to find the deleted nodes
        // replacement

        else 
        {

         BookNode replacement = getReplacementNode(focusNode);

        // If the focusNode is root replace root
        // with the replacement

        if (focusNode == root)
            root = replacement;

        // If the deleted node was a left child
        // make the replacement the left child

        else if (isItALeftChild)
            parent.leftChild = replacement;

        // Vice versa if it was a right child

        else
            parent.rightChild = replacement;

        replacement.leftChild = focusNode.leftChild;

        }

        return true;

    }
    
    public boolean removeNode_keyBookTitle(Book node) 
    {	 
        // Start at the top of the tree

        BookNode focusNode = root;
        BookNode parent = root;

        // When searching for a Node this will
        // tell us whether to search to the
        // right or left

        boolean isItALeftChild = true;

        // While we haven't found the Node
        // keep looking
        
//        JOptionPane.showMessageDialog(null, "Title: "+node.getBookTitle());

        while (!focusNode.b_Title.equalsIgnoreCase(node.getBookTitle())) 
        {

            parent = focusNode;

            // If we should search to the left

            if (node.getBookTitle().compareToIgnoreCase(focusNode.b_Title) < 0) 
            {

                isItALeftChild = true;

                // Shift the focus Node to the left child

                focusNode = focusNode.leftChild;

            } 
            else 
            {

                // Greater than focus node so go to the right

                isItALeftChild = false;

                // Shift the focus Node to the right child

                focusNode = focusNode.rightChild;

            }

            // The node wasn't found

            if (focusNode == null)
                return false;

        }

        // If Node doesn't have children delete it

        if (focusNode.leftChild == null && focusNode.rightChild == null) 
        {

            // If root delete it

        if (focusNode == root)
                root = null;

            // If it was marked as a left child
            // of the parent delete it in its parent

            else if (isItALeftChild)
                parent.leftChild = null;

            // Vice versa for the right child

            else
                parent.rightChild = null;

        }

        // If no right child

        else if (focusNode.rightChild == null) 
        {

            if (focusNode == root)
                root = focusNode.leftChild;

            // If focus Node was on the left of parent
            // move the focus Nodes left child up to the
            // parent node

            else if (isItALeftChild)
                parent.leftChild = focusNode.leftChild;

            // Vice versa for the right child

            else
                parent.rightChild = focusNode.leftChild;

        }

        // If no left child

        else if (focusNode.leftChild == null) 
        {

            if (focusNode == root)
                root = focusNode.rightChild;

            // If focus Node was on the left of parent
            // move the focus Nodes right child up to the
            // parent node

            else if (isItALeftChild)
                parent.leftChild = focusNode.rightChild;

            // Vice versa for the left child

            else
                parent.rightChild = focusNode.rightChild;

        }

        // Two children so I need to find the deleted nodes
        // replacement

        else 
        {

         BookNode replacement = getReplacementNode(focusNode);

        // If the focusNode is root replace root
        // with the replacement

        if (focusNode == root)
            root = replacement;

        // If the deleted node was a left child
        // make the replacement the left child

        else if (isItALeftChild)
            parent.leftChild = replacement;

        // Vice versa if it was a right child

        else
            parent.rightChild = replacement;

        replacement.leftChild = focusNode.leftChild;

        }

        return true;

    }
    
    public BookNode getReplacementNode(BookNode replacedNode) 
    {
	 
        BookNode replacementParent = replacedNode;
        BookNode replacement = replacedNode;

        BookNode focusNode = replacedNode.rightChild;

        // While there are no more left children

        while (focusNode != null) {

            replacementParent = replacement;

            replacement = focusNode;

            focusNode = focusNode.leftChild;

        }

        // If the replacement isn't the right child
        // move the replacement into the parents
        // leftChild slot and move the replaced nodes
        // right child into the replacements rightChild

        if (replacement != replacedNode.rightChild) {

            replacementParent.leftChild = replacement.rightChild;
            replacement.rightChild = replacedNode.rightChild;

        }

        return replacement;

    }
}

class BookNode 
{
    int b_No;
    int isbn;
    String b_Title;
    String au_FN;
    String au_SN;

    BookNode leftChild;
    BookNode rightChild;
    
    public BookNode()
    {
        this.b_No = 0;
        this.b_Title = null;
        this.isbn = 0;
        this.au_FN = null;
        this.au_SN = null;
        
        this.leftChild = null;
        this.rightChild = null;
    }

    public BookNode(Book newBook) 
    {
        this.b_No = newBook.getBookNo();
        this.b_Title = newBook.getBookTitle();
        this.isbn = newBook.getISBN();
        this.au_FN = newBook.getAuthorFirstName();
        this.au_SN = newBook.getAuthorSurname();
        
        this.leftChild = null;
        this.rightChild = null;
    }

    public String toString() 
    {
        return isbn+"   "+b_Title+"   "+au_FN+" "+au_SN;
    }
}