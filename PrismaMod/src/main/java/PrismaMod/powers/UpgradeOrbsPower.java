package PrismaMod.powers;

import PrismaMod.DefaultMod;
import PrismaMod.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;

public class UpgradeOrbsPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("UpgradeOrbsPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("PrismaModResources/images/powers/UpgradeOrbs_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("PrismaModResources/images/powers/UpgradeOrbs_power32.png");

    public UpgradeOrbsPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = true;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    public void atEndOfRound() {
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, FocusPower.POWER_ID));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, FocusPower.POWER_ID, 1));
        }
    }

    @Override
    public void atStartOfTurn() {

        //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(owner, source, new FocusPower(owner, 1)));
    }

    @Override
    public AbstractPower makeCopy() {
        return new UpgradeOrbsPower(owner, source, amount);
    }

}
