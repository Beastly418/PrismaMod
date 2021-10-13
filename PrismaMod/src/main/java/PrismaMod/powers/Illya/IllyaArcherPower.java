package PrismaMod.powers.Illya;

import PrismaMod.DefaultMod;
import PrismaMod.cards.IllyaCards.IllyaInstallCards.IllyaInstallObj;
import PrismaMod.powers.AbstractInstallPower;
import PrismaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.DiscoveryAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class IllyaArcherPower extends AbstractInstallPower {
    //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction((AbstractDungeon.player, AbstractDungeon.player, new DiscoveryAction()));
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("IllyaArcherPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static IllyaInstallObj install;

    public int NUM_RANDOM_CARDS;
    public int STRENGTH_INC;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("PrismaModResources/images/powers/Archer_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("PrismaModResources/images/powers/Archer_power32.png");

    public IllyaArcherPower(final AbstractCreature owner, final AbstractCreature source, final int amount, IllyaInstallObj install) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.install = install;

        NUM_RANDOM_CARDS = this.install.getNumRandomCards();
        STRENGTH_INC = this.install.getStrengthInc();

        type = PowerType.BUFF;
        isTurnBased = false;


        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0] + NUM_RANDOM_CARDS + DESCRIPTIONS[1] + STRENGTH_INC + DESCRIPTIONS[2];
    }

    @Override
    public AbstractPower makeCopy() {
        return new IllyaArcherPower(owner, source, amount, install);
    }

    @Override
    public void atStartOfTurn() {
        for (int i = 0; i < NUM_RANDOM_CARDS; i++) {
            AbstractDungeon.actionManager.addToTop(new DiscoveryAction());
        }
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(owner, source, new StrengthPower(owner, STRENGTH_INC)));
    }


}
