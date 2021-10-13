package PrismaMod.powers.monsters;

import PrismaMod.DefaultMod;
import PrismaMod.cards.IllyaCards.IllyaInstallCards.IllyaInstallObj;
import PrismaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.defect.DiscardPileToHandAction;
import com.megacrit.cardcrawl.actions.unique.AddCardToDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;


public class ShapelessIslandPower extends AbstractMonsterPower {

    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("ShapelessIslandPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static IllyaInstallObj install;
    public int turnNumber;

    private static final Texture tex84 = TextureLoader.getTexture("PrismaModResources/images/powers/ShapelessIsland84.png");
    private static final Texture tex32 = TextureLoader.getTexture("PrismaModResources/images/powers/ShapelessIsland32.png");

    public ShapelessIslandPower(final AbstractCreature owner, final AbstractCreature source, final int amount){
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.source = source;
        this.amount = amount;

        type = PowerType.BUFF;
        isTurnBased = true;

        this.turnNumber = 0;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        this.turnNumber++;
        if(turnNumber%4 == 0){
            AbstractCard card = AbstractDungeon.returnRandomCurse();
            if(this.owner != null && this.owner instanceof AbstractPlayer){
                ((AbstractPlayer) this.owner).hand.addToHand(card);
            }
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new ShapelessIslandPower(this.owner, this.source, this.amount);
    }
    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

}
