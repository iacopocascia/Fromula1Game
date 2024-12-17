package it.unicam.formula1Game.player;

import it.unicam.formula1Game.cell.Coordinate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CpuPlayerTest {
    private final CpuPlayer player=new CpuPlayer(0,new Coordinate(0,0));
    @Test
    public void cpu_player_creation_test(){
        assertFalse(player.hasCrashed());
        assertEquals(player.getLastMove(),new Coordinate(0,0));
        assertEquals(player.getVelocity(),0.0);
        assertEquals(player.getId(),0);
        assertEquals(player.getPosition(),new Coordinate(0,0));
    }
    @Test
    public void calculate_principal_point_test(){
        assertEquals(player.calculatePrincipalPoint(),new Coordinate(0,0));
    }
    @Test
    public void make_move_sequence_test(){
        player.makeMove(new Coordinate(0,1));
        assertEquals(player.getPosition(),new Coordinate(0,1));
        assertEquals(player.getLastMove(),new Coordinate(0,1));
        assertEquals(player.calculatePrincipalPoint(),new Coordinate(0,2));
        assertEquals(player.getVelocity(),1.0);
        player.makeMove(new Coordinate(1,3));
        assertEquals(player.getPosition(),new Coordinate(1,3));
        assertEquals(player.getLastMove(),new Coordinate(1,2));
        assertEquals(player.calculatePrincipalPoint(),new Coordinate(2,5));
        assertEquals(player.getVelocity(),Math.sqrt(5));
        player.makeMove(new Coordinate(3,4));
        assertEquals(player.getPosition(),new Coordinate(3,4));
        assertEquals(player.getLastMove(),new Coordinate(2,1));
        assertEquals(player.calculatePrincipalPoint(),new Coordinate(5,5));
        assertEquals(player.getVelocity(),Math.sqrt(5));


    }
}
