package PrismaMod.powers.Illya;

import PrismaMod.DefaultMod;
import PrismaMod.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static PrismaMod.cards.IllyaCards.Misc.IllyaZwei.STRENGTHDOWN;
import static PrismaMod.cards.IllyaCards.Misc.IllyaZwei.VULNERABLEUP;

public class IllyaZweiDebuffPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("IllyaZweiDebuffPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("PrismaModResources/images/powers/Zwei_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("PrismaModResources/images/powers/Zwei_power32.png");

    public IllyaZweiDebuffPower(final AbstractCreature owner, final AbstractCreature source, final int amount){
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.DEBUFF;
        isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atEndOfRound() {
        AbstractPlayer p = AbstractDungeon.player;
        AbstractPower pow = p.getPower(StrengthPower.POWER_ID);
        if (pow != null) {
            pow.amount = STRENGTHDOWN;
        } else {
            AbstractDungeon.actionManager.addToTurnStart(new ApplyPowerAction(p, p, new StrengthPower(p, STRENGTHDOWN)));
        }
        //AbstractDungeon.actionManager.addToTurnStart(new ApplyPowerAction(p, p, new StrengthPower(p, STRENGTHDOWN)));
        AbstractDungeon.actionManager.addToTurnStart(new ApplyPowerAction(p, p, new VulnerablePower(p, VULNERABLEUP, true)));
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToTop(new LoseEnergyAction(AbstractDungeon.player.energy.energy));
    }


    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        //AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.hand));

    }

    @Override
    public AbstractPower makeCopy() {
        return new IllyaZweiPower(owner, source, amount);
    }
}
