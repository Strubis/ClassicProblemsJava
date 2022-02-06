package chapter01;

import java.util.BitSet;

/**
 * Compress�o de dados.
 * 
 * @author EmersonPC
 */
public class CompressedGene {
    
    // Conjunto arbitrário de bits
    private static BitSet bitSet;
    private static int length;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        //String gene = args[1];
        String gene = 
                "TAGGGATTAACCGTTATATATATATAGCCATGGATCGATTATATAGGGATTAACCGTTATAT"
                + "ATATATAGCCATGGATCGATTATA";
        
        compress( gene );
        final String decompressed = decompress();
        
        System.out.println(decompressed);
        System.out.println("O gene original eh igual ao descompressado: " + 
                gene.equalsIgnoreCase(decompressed) );
    }
    
    /**
     * O método ao receber uma string que identifica um gene compressa a mesma
     * e armazena em um conjunto de bits, que terão seus valores representados
     * por valores binários, ex.: A -> 00.
     * 
     * @param gene recebe uma string correspondente de um gene.
     */
    private static void compress(String gene){
        length = gene.length();
        
        // Reserva a capacidade ideal para todos os bits
        bitSet = new BitSet( length * 2 );
        
        // Para fins de consistência convertemos em Upper Case
        final String upperGene = gene.toUpperCase();
        
        // Convertemos a string para sua representação em bits
        for (int i = 0; i < length; i++) {
            final int firstLocation = 2 * i;
            final int secondLocation = 2 * i + 1;
            
            switch( gene.charAt(i) ){
                case 'A': // Os próximos dois bits são 00
                    bitSet.set( firstLocation, false );
                    bitSet.set( secondLocation, false );
                    break;
                case 'C': // Os próximos dois bits são 01
                    bitSet.set( firstLocation, false );
                    bitSet.set( secondLocation, true );
                    break;
                case 'G': // Os próximos dois bits são 10
                    bitSet.set( firstLocation, true );
                    bitSet.set( secondLocation, false );
                    break;
                case 'T': // Os próximos dois bits são 11
                    bitSet.set( firstLocation, true );
                    bitSet.set( secondLocation, true );
                    break;
                default:
                    throw new IllegalArgumentException( "O gene enviado contem "
                            + "outros caracteres alem dos permitidos - ACGT" );
            }
        }
        
    }
    
    /**
     * O método pega o conjunto compressado de bits que representam o gene,
     * e a partir dele descompressa o mesmo de acordo com os bits responsáveis
     * de cada um, ex.: 00 -> A.
     * 
     * @return retorna o gene original.
     */
    private static String decompress(){
        if( bitSet == null ) return "";
        
        StringBuilder builder = new StringBuilder( length );
        
        for (int i = 0; i < (length * 2); i += 2) {
            final int firstBit = ( bitSet.get(i) ? 1 : 0 );
            final int secondBit = ( bitSet.get(i + 1) ? 1 : 0 );
            
            /* Usando o operador '<<' todos os espaços da esquerda atrás são 
            substítuidos por 0's, o operador '|' diz que "se os valores dos bits
            são um 1, então coloque 1", por isso quando se combina 0 com os valores
            do segundo bit irão retorna-lo.
            */
            final int lastBits = firstBit << 1 | secondBit;
            
            switch( lastBits ){
                case 0b00: // 00 = A
                    builder.append( 'A' );
                    break;
                case 0b01: // 01 = C
                    builder.append( 'C' );
                    break;
                case 0b10: // 10 = G
                    builder.append( 'G' );
                    break;
                case 0b11: // 11 = T
                    builder.append( 'T' );
                    break;
            }
        }
        
        return builder.toString();
    }
}
