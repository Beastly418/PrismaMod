package PrismaMod.powers.Illya;

import PrismaMod.DefaultMod;
import PrismaMod.cards.IllyaCards.IllyaInstallCards.IllyaInstallObj;
import PrismaMod.powers.AbstractInstallPower;
import PrismaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.*;

public class IllyaRulerPower extends AbstractInstallPower {
    //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction((AbstractDungeon.player, AbstractDungeon.player, new DiscoveryAction()));
    //APPLY THESE TO ALL OF THE ENEMIES
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("IllyaRulerPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static IllyaInstallObj install;

    public boolean INVULNERABLE;
    public int BENEFICIAL;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("PrismaModResources/images/powers/Ruler_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("PrismaModResources/images/powers/Ruler_power32.png");

    public IllyaRulerPower(final AbstractCreature owner, final AbstractCreature source, final int amount, IllyaInstallObj install) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.install = install;

        INVULNERABLE = this.install.isInvulnerable();
        BENEFICIAL = this.install.getNumBeneficial();

        type = PowerType.BUFF;
        isTurnBased = false;


        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + BENEFICIAL + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new IllyaRulerPower(owner, source, amount, install);
    }


    @Override
    public void atStartOfTurn() {
        install.makeRuler();
        //if (install.isInvulnerable()) {
        //    AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(owner, source, new IntangiblePlayerPower(owner, 1)));
        //}
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(owner, owner, new StrengthPower(owner, BENEFICIAL)));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(owner, owner, new DexterityPower(owner, BENEFICIAL)));
        //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(owner, owner, new EnergizedPower(owner, BENEFICIAL)));
    }

}

