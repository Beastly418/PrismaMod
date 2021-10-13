package PrismaMod.powers.monsters;

import PrismaMod.DefaultMod;
import PrismaMod.cards.IllyaCards.IllyaInstallCards.IllyaInstallObj;
import PrismaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;

//JUST A PLACEHOLDER POWER THE IMPLEMENTATION IS GOING TO BE IN THE METHOD ON SABER ALTER

public class MagicResistancePower extends AbstractMonsterPower {

    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("ManaBurstPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static IllyaInstallObj install;

    private boolean removable;

    private static final Texture tex84 = TextureLoader.getTexture("PrismaModResources/images/powers/MagicResistance84.png");
    private static final Texture tex32 = TextureLoader.getTexture("PrismaModResources/images/powers/MagicResistance32.png");

    @Deprecated
    public MagicResistancePower(final AbstractCreature owner, final AbstractCreature source, final int amount){
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.source = source;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = true;

        removable = false;

        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        if (!removable) {
            removable = true;
        } else {
            if (this.amount == 0) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
            } else {
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
            }
        }
        AbstractDungeon.actionManager.addToTop(new GainBlockAction(this.owner, this.owner, 10));
    }

    @Override
    public AbstractPower makeCopy() {
        return new MagicResistancePower(this.owner, this.source, this.amount);
    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

}