package Tests;

import java.util.Random;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import StarTrek.Game;
import StarTrek.Torpedoes;
import StarTrek.Util;
import StarTrek.Weapon;

public class PhotonCharacterizationTests {
    private Game game;
    private MockGalaxy context;    
    private Torpedoes T = new Torpedoes();

    @After
    public void RemoveTheMockRandomGeneratorForOtherTests_IReallyWantToRefactorThatStaticVariableSoon() {
        Util.generator = new Random();
    }

    @Before
    public void setUp() {
        game = new Game();
        context = new MockGalaxy();
        context.setValueForTesting("command", "photon");
    }

    @Test
    public void NotifiedIfNoTorpedoesRemain() {
        T.setTorpedoes(0);
        context.setValueForTesting("target", new MockKlingon(2000, 200));
        T.fireWeapon(context,game.EnergyRemaining());
        Assert.assertEquals("No more photon torpedoes! || ",
            context.getAllOutput());
    }

    @Test
    public void TorpedoMissesDueToRandomFactors() {
        int distanceWhereRandomFactorsHoldSway = 2500;
        context.setValueForTesting("target", new MockKlingon(distanceWhereRandomFactorsHoldSway, 200));
        Util.generator = new MockRandom(); // without this the test would often fail
        T.fireWeapon(context,game.EnergyRemaining());
        Assert.assertEquals("Torpedo missed Klingon at 2500 sectors... || ",
            context.getAllOutput());
        Assert.assertEquals(7, T.getTorpedoes());
    }

    @Test
    public void TorpedoMissesDueToDistanceAndCleverKlingonEvasiveActions() {
        int distanceWhereTorpedoesAlwaysMiss = 3500;
        context.setValueForTesting("target", new MockKlingon(distanceWhereTorpedoesAlwaysMiss, 200));
        T.fireWeapon(context,game.EnergyRemaining());
        Assert.assertEquals("Torpedo missed Klingon at 3500 sectors... || ",
            context.getAllOutput());
        Assert.assertEquals(7, T.getTorpedoes());
    }

    @Test
    public void TorpedoDestroysKlingon() {
        MockKlingon klingon = new MockKlingon(500, 200);
        context.setValueForTesting("target", klingon);
        Util.generator = new MockRandom();
        T.fireWeapon(context,game.EnergyRemaining());
        Assert.assertEquals("Photons hit Klingon at 500 sectors with 825 units || Klingon destroyed! || ",
            context.getAllOutput());
        Assert.assertEquals(7, T.getTorpedoes());
        Assert.assertTrue(klingon.deleteWasCalled());

    }

    @Test
    public void TorpedoDamagesKlingon() {
        context.setValueForTesting("target", new MockKlingon(500, 2000));
        Util.generator = new MockRandom();
        T.fireWeapon(context,game.EnergyRemaining());
        Assert.assertEquals("Photons hit Klingon at 500 sectors with 825 units || Klingon has 1175 remaining || ",
            context.getAllOutput());
        Assert.assertEquals(7, T.getTorpedoes());
    }

}
