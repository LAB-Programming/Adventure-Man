public class BadBlockException extends RuntimeException{
    public final Block b;
    BadBlockException(String s, Block b){
        super(s);
        this.b=b;
    }
}