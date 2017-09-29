import java.util.*;
import java.io.*;
class Node{


    protected int element;
    protected Node next;

    public Node(){next=null;
        element=0;
    }

    public Node(int e ,Node n){
        element=e;
        next=n;
    }
    public int getData(){
        return element;
    }

    public int getElement() {
        return element;
    }
    public void setData(int d){
        element=d;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node n) {
        next=n;
    }
}
class LinkList{
    public Node head;
    private Node tail;
    private int size;
    public LinkList(){
        head=null;
        tail=null;
        size=0;
    }       //constructor for linked list
    public int size(){return size;}
    public boolean isEmpty(){return size==0;}
    public int first(){
        return head.getElement();
    }
    public int last(){
        return tail.getElement();
    }
    public void addFirst(int e){
        size++;
        if(head== null){head.setData(e);head.setNext(null);}
        else{Node newNode=new Node(e,head);head=newNode;}
    }
    public void addLast(int e){
        Node newest=new Node(e,null);
        if (isEmpty()){
            head=newest;
            tail=head;
        }
        else {tail.setNext(newest);tail=newest;}
        size++;
    }
    public int removeFirst(){
        int answer=head.getElement();
        head=head.getNext();
        size--;
        if (size==0){
            tail=null;
        }
        return answer;
    }
}

public class LinkedListImage implements CompressedImageInterface{
    public LinkList[] c;
    public int height;
    public int width;
    public int[] arr;
    public int[] arr2;
    public LinkedListImage(String filename)
    {
        try {
            FileInputStream fstream=new FileInputStream(filename);
            Scanner scan=new Scanner(fstream);
            width=scan.nextInt();
            height=scan.nextInt();
            LinkList[] compressed = new LinkList[height+1];
            c=compressed;
            compressed[0]=new LinkList();
            compressed[0].addLast(width);
            compressed[0].addLast(height);
            //System.out.println(compressed[1].getClass().getName())
            for (int i = 1; i < height+1; i++) {
                compressed[i]=new LinkList();
                LinkList temp = new LinkList();
                for (int j = 0; j < width; j++) {
                    int n;
                    n=scan.nextInt();
                    if (n==0 && j == width - 1) {
                        temp.addLast(j);
                        if (temp.size() == 1) {
                            compressed[i].addLast(temp.first());
                            compressed[i].addLast(temp.first());
                        } else if (temp.size() > 1) {
                            compressed[i].addLast(temp.first());
                            compressed[i].addLast(temp.last());
                        }
                        temp = new LinkList();
                    }
                    else if (n==0) {
                        temp.addLast(j);
                    }
                    else {
                        if (temp.size() == 1) {
                            compressed[i].addLast(temp.first());
                            compressed[i].addLast(temp.last());
                        }
                        else if (temp.size() > 1) {
                            compressed[i].addLast(temp.first());
                            compressed[i].addLast(temp.last());


                        }
                        else{
                            continue;
                        }
                        temp = new LinkList();
                    }
                }
                compressed[i].addLast(-1);

            }
        }
        catch (FileNotFoundException e){
            System.out.println("File not Found");
        }

        //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public LinkedListImage(boolean[][] grid, int wh, int ht)
    {
        width=wh;
        height=ht;
        LinkList[] compressed2 = new LinkList[height+1];
        c=compressed2;
        compressed2[0]=new LinkList();
        compressed2[0].addLast(width);
        compressed2[0].addLast(height);
        int h=0;
        for (int i = 1; i < height+1; i++) {
            compressed2[i]=new LinkList();
            LinkList temp = new LinkList();
            for (int j = 0; j < width; j++) {
                int n;
                if (grid[h][j]){n=1;}
                else{n=0;}
                if (n==0 && j == width - 1) {
                    temp.addLast(j);
                    if (temp.size() == 1) {
                        compressed2[i].addLast(temp.first());
                        compressed2[i].addLast(temp.first());
                    } else if (temp.size() > 1) {
                        compressed2[i].addLast(temp.first());
                        compressed2[i].addLast(temp.last());
                    }
                    temp = new LinkList();
                }
                else if (n==0) {
                    temp.addLast(j);
                }
                else {
                    if (temp.size() == 1) {
                        compressed2[i].addLast(temp.first());
                        compressed2[i].addLast(temp.last());
                    }
                    else if (temp.size() > 1) {
                        compressed2[i].addLast(temp.first());
                        compressed2[i].addLast(temp.last());
                    }
                    else{
                        continue;
                    }
                    temp = new LinkList();
                }
            }
            compressed2[i].addLast(-1);
            h++;
        }
    }

    public boolean getPixelValue(int x, int y) throws PixelOutOfBoundException
    {
        if (y<=width-1 && x<=height-1 && x>=0 &&y>=0) {
            int n = c[x+1].size() - 1;
            Node ptr = c[x+1].head;
            for (int j = 0; j < n; j = j + 2) {
                int a = ptr.getData();
                Node ptr2 = ptr.getNext();
                int b = ptr2.getData();
                ptr=ptr2.getNext();
                if (y >= a && y <= b) {
                    return false;
                }
            }
            return true;
        }
        else{
            throw new PixelOutOfBoundException("Check the height and width of input");
        }
    }


    public void setPixelValue(int x, int y, boolean val) throws PixelOutOfBoundException
    {
        if (y <= width - 1 && x <= height - 1 && x >= 0 && y >= 0) {
            arr = new int[width];
            LinkList list = c[x + 1];
            Node ptr = list.head;
            Node p = list.head;
            for (int j = 0; j < list.size() - 2; j++) {
                p = p.getNext();
            }
            int n = list.size() - 1;
            int t1 = 0;
            int count = 0;
            if (list.size() > 1) {
                for (int j = 0; j < n; j = j + 2) {
                    int a = ptr.getData();
                    Node ptr2 = ptr.getNext();
                    int b = ptr2.getData();
                    ptr = ptr2.getNext();
                    for (int k = 0; k < a - t1; k++) {
                        arr[count] = 1;
                        count++;
                    }
                    for (int l = 0; l < b - a + 1; l++) {
                        arr[count] = 0;
                        count++;
                    }
                    t1 = b + 1;
                }
                for (int j = 0; j < (width - 1 - p.getElement()); j++) {
                    arr[count] = 1;
                    count++;
                }
            } else {
                for (int v = 0; v < width; v++) {
                    arr[count] = 1;
                    count++;
                }
            }
            if (val) {
                arr[y] = 1;
            } else {
                arr[y] = 0;
            }

            LinkList list2 = new LinkList();
            LinkList temp = new LinkList();
            for (int j = 0; j < width; j++) {
                if (arr[j] == 0 && j == width - 1) {
                    temp.addLast(j);
                    if (temp.size() == 1) {
                        list2.addLast(temp.first());
                        list2.addLast(temp.first());
                    } else if (temp.size() > 1) {
                        list2.addLast(temp.first());
                        list2.addLast(temp.last());
                    }
                    temp = new LinkList();
                } else if (arr[j] == 0) {
                    temp.addLast(j);
                } else {
                    if (temp.size() == 1) {
                        list2.addLast(temp.first());
                        list2.addLast(temp.last());
                    } else if (temp.size() > 1) {
                        list2.addLast(temp.first());
                        list2.addLast(temp.last());
                    } else {
                        continue;
                    }
                    temp = new LinkList();
                }
            }
            list2.addLast(-1);
            c[x + 1] = list2;

        }
        else{
            throw new PixelOutOfBoundException("Check the height and width of input");
        }
    }

    public int[] numberOfBlackPixels()
    {
        int[] black=new int[height];
        for (int i = 1; i <height+1 ; i++) {
            int n=c[i].size()-1;
            int bl=0;
            if (n==0) {continue;}
            else {
                Node ptr=c[i].head;
                for (int j = 0; j <n ; j=j+2) {
                    int a=ptr.getData();
                    Node ptr2=ptr.getNext();
                    int b=ptr2.getData();
                    bl=bl+b-a+1;
                    ptr=ptr2.getNext();
                }
            }
            black[i-1]=bl;
        }
        return black;
        //you need to implement this
        //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }

    public void invert()
    {
        for (int i = 1; i <height+1; i++) {
            LinkList temp=new LinkList();
            int t=0;int x=0;
            Node ptr=c[i].head;
            if (c[i].size()!=1) {
                for (int j = 0; j < c[i].size() - 1; j = j + 2) {
                    int a = ptr.getElement();
                    Node ptr2 = ptr.getNext();
                    int b = ptr2.getElement();
                    x = b;
                    ptr = ptr2.getNext();
                    if (a == 0) {
                        continue;
                    } else {
                        temp.addLast(t);
                        temp.addLast(a - 1);
                    }
                    t = a + 1;

                }
                if (x != width - 1) {
                    temp.addLast(x + 1);
                    temp.addLast(width-1);
                }
                temp.addLast(-1);
                c[i] = temp;
            }
            else{temp.addLast(0);
                temp.addLast(width-1);
                temp.addLast(-1);
                c[i]=temp;
            }
        }
    }


    public void performAnd(CompressedImageInterface img) throws BoundsMismatchException
    {
        LinkedListImage img2=(LinkedListImage)img;
        LinkList[] imglist=img2.c;
        if(img2.height==height && img2.width==width) {
            for (int var = 0; var < height; var++) {
                arr = new int[width];
                LinkList list = c[var + 1];
                Node ptr = list.head;
                Node p = list.head;
                for (int j = 0; j < list.size() - 2; j++) {
                    p = p.getNext();
                }
                int n = list.size() - 1;
                int t1 = 0;
                int count = 0;
                if (list.size() > 1) {
                    for (int j = 0; j < n; j = j + 2) {
                        int a = ptr.getData();
                        Node ptr2 = ptr.getNext();
                        int b = ptr2.getData();
                                ptr = ptr2.getNext();
                        for (int k = 0; k < a - t1; k++) {
                            arr[count] = 1;
                            count++;
                        }
                        for (int l = 0; l < b - a + 1; l++) {
                            arr[count] = 0;
                            count++;
                        }
                        t1 = b + 1;
                    }
                    for (int j = 0; j < (width - 1 - p.getElement()); j++) {
                        arr[count] = 1;
                        count++;
                    }
                } else {
                    for (int v = 0; v < width; v++) {
                        arr[count] = 1;
                        count++;
                    }
                }


                ///////////////////
                arr2 = new int[width];
                LinkList list2 = imglist[var + 1];
                Node pt = list2.head;
                Node x = list2.head;
                for (int j = 0; j < list2.size() - 2; j++) {
                    x = x.getNext();
                }
                int n2 = list2.size() - 1;
                int t2 = 0;
                int count2 = 0;
                if (list2.size() > 1) {
                    for (int j = 0; j < n2; j = j + 2) {
                        int a = pt.getData();
                        Node pt2 = pt.getNext();
                        int b = pt2.getData();
                        pt = pt2.getNext();
                        for (int k = 0; k < a - t2; k++) {
                            arr2[count2] = 1;
                            count2++;
                        }
                        for (int l = 0; l < b - a + 1; l++) {
                            arr2[count2] = 0;
                            count2++;
                        }
                        t2 = b + 1;
                    }
                    for (int j = 0; j < (width - 1 - x.getElement()); j++) {
                        arr2[count2] = 1;
                        count2++;
                    }
                } else {
                    for (int v = 0; v < width; v++) {
                        arr2[count2] = 1;
                        count2++;
                    }
                }

                //////////////////////
                int[] arr3 = new int[width];
                for (int i = 0; i < width; i++) {
                    if (arr[i] == 1 && arr2[i] == 1) {
                        arr3[i] = 1;
                    } else {
                        arr3[i] = 0;
                    }
                }
                /////////////////////

                LinkList list3 = new LinkList();
                LinkList temp = new LinkList();
                for (int j = 0; j < width; j++) {
                    if (arr3[j] == 0 && j == width - 1) {
                        temp.addLast(j);
                        if (temp.size() == 1) {
                            list3.addLast(temp.first());
                            list3.addLast(temp.first());
                        } else if (temp.size() > 1) {
                            list3.addLast(temp.first());
                            list3.addLast(temp.last());
                        }
                        temp = new LinkList();
                    } else if (arr3[j] == 0) {
                        temp.addLast(j);
                    } else {
                        if (temp.size() == 1) {
                            list3.addLast(temp.first());
                            list3.addLast(temp.last());
                        } else if (temp.size() > 1) {
                            list3.addLast(temp.first());
                            list3.addLast(temp.last());
                        } else {
                            continue;
                        }
                        temp = new LinkList();
                    }
                }
                list3.addLast(-1);
                c[var + 1] = list3;
            }
        }
        else{
            throw new BoundsMismatchException("The Size of the two images do not match");
        }
    }

    public void performOr(CompressedImageInterface img) throws BoundsMismatchException
    {
        LinkedListImage img2=(LinkedListImage)img;
        LinkList[] imglist=img2.c;
        if(img2.height==height && img2.width==width) {
            for (int var = 0; var < height; var++) {
                arr = new int[width];
                LinkList list = c[var + 1];
                Node ptr = list.head;
                Node p = list.head;
                for (int j = 0; j < list.size() - 2; j++) {
                    p = p.getNext();
                }
                int n = list.size() - 1;
                int t1 = 0;
                int count = 0;
                if (list.size() > 1) {
                    for (int j = 0; j < n; j = j + 2) {
                        int a = ptr.getData();
                        Node ptr2 = ptr.getNext();
                        int b = ptr2.getData();
                        ptr = ptr2.getNext();
                        for (int k = 0; k < a - t1; k++) {
                            arr[count] = 1;
                            count++;
                        }
                        for (int l = 0; l < b - a + 1; l++) {
                            arr[count] = 0;
                            count++;
                        }
                        t1 = b + 1;
                    }
                    for (int j = 0; j < (width - 1 - p.getElement()); j++) {
                        arr[count] = 1;
                        count++;
                    }
                } else {
                    for (int v = 0; v < width; v++) {
                        arr[count] = 1;
                        count++;
                    }
                }


                ///////////////////
                arr2 = new int[width];
                LinkList list2 = imglist[var + 1];
                Node pt = list2.head;
                Node x = list2.head;
                for (int j = 0; j < list2.size() - 2; j++) {
                    x = x.getNext();
                }
                int n2 = list2.size() - 1;
                int t2 = 0;
                int count2 = 0;
                if (list2.size() > 1) {
                    for (int j = 0; j < n2; j = j + 2) {
                        int a = pt.getData();
                        Node pt2 = pt.getNext();
                        int b = pt2.getData();
                        pt = pt2.getNext();
                        for (int k = 0; k < a - t2; k++) {
                            arr2[count2] = 1;
                            count2++;
                        }
                        for (int l = 0; l < b - a + 1; l++) {
                            arr2[count2] = 0;
                            count2++;
                        }
                        t2 = b + 1;
                    }
                    for (int j = 0; j < (width - 1 - x.getElement()); j++) {
                        arr2[count2] = 1;
                        count2++;
                    }
                } else {
                    for (int v = 0; v < width; v++) {
                        arr2[count2] = 1;
                        count2++;
                    }
                }

                //////////////////////
                int[] arr3 = new int[width];
                for (int i = 0; i < width; i++) {
                    if (arr[i] == 1 || arr2[i] == 1) {
                        arr3[i] = 1;
                    } else {
                        arr3[i] = 0;
                    }
                }
                /////////////////////

                LinkList list3 = new LinkList();
                LinkList temp = new LinkList();
                for (int j = 0; j < width; j++) {
                    if (arr3[j] == 0 && j == width - 1) {
                        temp.addLast(j);
                        if (temp.size() == 1) {
                            list3.addLast(temp.first());
                            list3.addLast(temp.first());
                        } else if (temp.size() > 1) {
                            list3.addLast(temp.first());
                            list3.addLast(temp.last());
                        }
                        temp = new LinkList();
                    } else if (arr3[j] == 0) {
                        temp.addLast(j);
                    } else {
                        if (temp.size() == 1) {
                            list3.addLast(temp.first());
                            list3.addLast(temp.last());
                        } else if (temp.size() > 1) {
                            list3.addLast(temp.first());
                            list3.addLast(temp.last());
                        } else {
                            continue;
                        }
                        temp = new LinkList();
                    }
                }
                list3.addLast(-1);
                c[var + 1] = list3;
            }
        }
        else{
            throw new BoundsMismatchException("The Size of the two images do not match");
        }
    }

    public void performXor(CompressedImageInterface img) throws BoundsMismatchException
    {
            LinkedListImage img2 = (LinkedListImage) img;
            LinkList[] imglist = img2.c;
            if (img2.height == height && img2.width == width) {
                for (int var = 0; var < height; var++) {
                    arr = new int[width];
                    LinkList list = c[var + 1];
                    Node ptr = list.head;
                    Node p = list.head;
                    for (int j = 0; j < list.size() - 2; j++) {
                        p = p.getNext();
                    }
                    int n = list.size() - 1;
                    int t1 = 0;
                    int count = 0;
                    if (list.size() > 1) {
                        for (int j = 0; j < n; j = j + 2) {
                            int a = ptr.getData();
                            Node ptr2 = ptr.getNext();
                            int b = ptr2.getData();
                            ptr = ptr2.getNext();
                            for (int k = 0; k < a - t1; k++) {
                                arr[count] = 1;
                                count++;
                            }
                            for (int l = 0; l < b - a + 1; l++) {
                                arr[count] = 0;
                                count++;
                            }
                            t1 = b + 1;
                        }
                        for (int j = 0; j < (width - 1 - p.getElement()); j++) {
                            arr[count] = 1;
                            count++;
                        }
                    } else {
                        for (int v = 0; v < width; v++) {
                            arr[count] = 1;
                            count++;
                        }
                    }


                    ///////////////////
                    arr2 = new int[width];
                    LinkList list2 = imglist[var + 1];
                    Node pt = list2.head;
                    Node x = list2.head;
                    for (int j = 0; j < list2.size() - 2; j++) {
                        x = x.getNext();
                    }
                    int n2 = list2.size() - 1;
                    int t2 = 0;
                    int count2 = 0;
                    if (list2.size() > 1) {
                        for (int j = 0; j < n2; j = j + 2) {
                            int a = pt.getData();
                            Node pt2 = pt.getNext();
                            int b = pt2.getData();
                            pt = pt2.getNext();
                            for (int k = 0; k < a - t2; k++) {
                                arr2[count2] = 1;
                                count2++;
                            }
                            for (int l = 0; l < b - a + 1; l++) {
                                arr2[count2] = 0;
                                count2++;
                            }
                            t2 = b + 1;
                        }
                        for (int j = 0; j < (width - 1 - x.getElement()); j++) {
                            arr2[count2] = 1;
                            count2++;
                        }
                    } else {
                        for (int v = 0; v < width; v++) {
                            arr2[count2] = 1;
                            count2++;
                        }
                    }

                    //////////////////////
                    int[] arr3 = new int[width];
                    for (int i = 0; i < width; i++) {
                        if (arr[i] == 1 && arr2[i] == 1) {
                            arr3[i] = 0;
                        } else if (arr[i] == 0 && arr2[i] == 0) {
                            arr3[i] = 0;
                        } else {
                            arr3[i] = 1;
                        }
                    }
                    /////////////////////

                    LinkList list3 = new LinkList();
                    LinkList temp = new LinkList();
                    for (int j = 0; j < width; j++) {
                        if (arr3[j] == 0 && j == width - 1) {
                            temp.addLast(j);
                            if (temp.size() == 1) {
                                list3.addLast(temp.first());
                                list3.addLast(temp.first());
                            } else if (temp.size() > 1) {
                                list3.addLast(temp.first());
                                list3.addLast(temp.last());
                            }
                            temp = new LinkList();
                        } else if (arr3[j] == 0) {
                            temp.addLast(j);
                        } else {
                            if (temp.size() == 1) {
                                list3.addLast(temp.first());
                                list3.addLast(temp.last());
                            } else if (temp.size() > 1) {
                                list3.addLast(temp.first());
                                list3.addLast(temp.last());
                            } else {
                                continue;
                            }
                            temp = new LinkList();
                        }
                    }
                    list3.addLast(-1);
                    c[var + 1] = list3;
                }
            }
            else{
                throw new BoundsMismatchException("The Size of the two images do not match");
            }
    }

    public String toStringUnCompressed()
    {
        String result="";
        result=result+width;result+=" ";result+=height;
        for (int i = 0; i <height ; i++) {
            result+=",";
            Node ptr=c[i+1].head;
            Node p=c[i+1].head;
            for (int j = 0; j <c[i+1].size()-2; j++) {
                p=p.getNext();
            }
            int n=c[i+1].size()-1;
            int t1=0;
            if (c[i+1].size()>1) {
                for (int j = 0; j < n; j = j + 2) {
                    int a = ptr.getData();
                    Node ptr2 = ptr.getNext();
                    int b = ptr2.getData();
                    ptr = ptr2.getNext();
                    for (int k = 0; k < a - t1; k++) {
                        result+=" ";result += "1";
                    }
                    for (int l = 0; l < b - a + 1; l++) {
                        result += " ";
                        result += "0";
                    }
                    t1 = b + 1;
                }
                for (int j = 0; j < (width-1-p.getElement()); j++) {
                    result+=" ";result+="1";
                    //System.out.print(p.getElement());
                }
            }
            else{
                for (int v= 0;v<width ;v++) {
                    result+=" ";result+="1";
                }
            }
        }
        return result;

    }

    public String toStringCompressed()
    {
        String result = "";
        result +=width;result +=" ";result +=height;result +=",";result+=" ";
        for(int i=1;i<height;i++){
            Node ptr=c[i].head;
            while(ptr.getNext()!=null){int h=ptr.getData();result+=h;result+=" ";ptr=ptr.getNext();}
            result+="-1";result+=",";result+=" ";
        }
        Node ptr=c[height].head; while(ptr.getNext()!=null){int h=ptr.getData();result+=h;result+=" ";ptr=ptr.getNext();}
        result+="-1";
        return result;
        //throw new java.lang.UnsupportedOperationException("Not implemented yet.");
    }


    public static void main(String[] args) {
        // testing all methods here :
        boolean success = true;

        // check constructor from file
        CompressedImageInterface img1 = new LinkedListImage("sampleInputFile.txt");

        // check toStringCompressed
        String img1_compressed = img1.toStringCompressed();
        String img_ans = "16 16, -1, 5 7 -1, 3 7 -1, 2 7 -1, 2 2 6 7 -1, 6 7 -1, 6 7 -1, 4 6 -1, 2 4 -1, 2 3 14 15 -1, 2 2 13 15 -1, 11 13 -1, 11 12 -1, 10 11 -1, 9 10 -1, 7 9 -1";
        success = success && (img_ans.equals(img1_compressed));
        if (!success)
        {
            System.out.println("Constructor (file) or toStringCompressed ERROR");
            return;
        }

        // check getPixelValue
        boolean[][] grid = new boolean[16][16];
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
            {
                try
                {
                    grid[i][j] = img1.getPixelValue(i, j);
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
            }

        // check constructor from grid
        CompressedImageInterface img2 = new LinkedListImage(grid, 16, 16);
        String img2_compressed = img2.toStringCompressed();
        success = success && (img2_compressed.equals(img_ans));
        if (!success)
        {
            System.out.println("Constructor (array) or toStringCompressed ERROR");
            return;
        }

        // check Xor
        try
        {
            img1.performXor(img2);
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
            {
                try
                {
                    success = success && (!img1.getPixelValue(i,j));
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
            }

        if (!success)
        {
            System.out.println("performXor or getPixelValue ERROR");
            return;
        }

        // check setPixelValue
        for (int i = 0; i < 16; i++)
        {
            try
            {
                img1.setPixelValue(i, 0, true);
            }
            catch (PixelOutOfBoundException e)
            {
                System.out.println("Errorrrrrrrr");
            }
        }
        // check numberOfBlackPixels
        int[] img1_black = img1.numberOfBlackPixels();
        success = success && (img1_black.length == 16);
        for (int i = 0; i < 16 && success; i++)
            success = success && (img1_black[i] == 15);
        if (!success)
        {
            System.out.println("setPixelValue or numberOfBlackPixels ERROR");
            return;
        }

        // check invert
        img1.invert();
        for (int i = 0; i < 16; i++)
        {
            try
            {
                success = success && !(img1.getPixelValue(i, 0));
            }
            catch (PixelOutOfBoundException e)
            {
                System.out.println("Errorrrrrrrr");
            }
        }
        if (!success)
        {
            System.out.println("invert or getPixelValue ERROR");
            return;
        }

        // check Or
        try
        {
            img1.performOr(img2);
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
            {
                try
                {
                    success = success && img1.getPixelValue(i,j);
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
            }
        if (!success)
        {
            System.out.println("performOr or getPixelValue ERROR");
            return;
        }

        // check And
        try
        {
            img1.performAnd(img2);
        }
        catch (BoundsMismatchException e)
        {
            System.out.println("Errorrrrrrrr");
        }
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 16; j++)
            {
                try
                {
                    success = success && (img1.getPixelValue(i,j) == img2.getPixelValue(i,j));
                }
                catch (PixelOutOfBoundException e)
                {
                    System.out.println("Errorrrrrrrr");
                }
            }
        if (!success)
        {
            System.out.println("performAnd or getPixelValue ERROR");
            return;
        }

        // check toStringUnCompressed
        String img_ans_uncomp = "16 16, 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1, 1 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1, 1 1 1 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 0 0 0 0 0 1 1 1 1 1 1 1 1, 1 1 0 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 1 1 0 0 1 1 1 1 1 1 1 1, 1 1 1 1 0 0 0 1 1 1 1 1 1 1 1 1, 1 1 0 0 0 1 1 1 1 1 1 1 1 1 1 1, 1 1 0 0 1 1 1 1 1 1 1 1 1 1 0 0, 1 1 0 1 1 1 1 1 1 1 1 1 1 0 0 0, 1 1 1 1 1 1 1 1 1 1 1 0 0 0 1 1, 1 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1, 1 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1, 1 1 1 1 1 1 1 1 1 0 0 1 1 1 1 1, 1 1 1 1 1 1 1 0 0 0 1 1 1 1 1 1";
        success = success && (img1.toStringUnCompressed().equals(img_ans_uncomp)) && (img2.toStringUnCompressed().equals(img_ans_uncomp));
        if (!success)
        {
            System.out.println("toStringUnCompressed ERROR");
            return;
        }
        else
            System.out.println("ALL TESTS SUCCESSFUL! YAYY!");
    }
}