package PrismaMod.powers.Illya;

import PrismaMod.DefaultMod;
import PrismaMod.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

public class IllyaZweiPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("IllyaZweiPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("PrismaModResources/images/powers/Zwei_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("PrismaModResources/images/powers/Zwei_power32.png");

    public IllyaZweiPower(final AbstractCreature owner, final AbstractCreature source, final int amount){
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
        MonsterGroup m = AbstractDungeon.getMonsters();
        int amt = 0;
        for(AbstractMonster mon : m.monsters){
            if (mon.hasPower(PoisonPower.POWER_ID)){
                amt = mon.getPower(PoisonPower.POWER_ID).amount;
                AbstractDungeon.actionManager.addToTop(new DamageAction(mon, new DamageInfo(mon, amt)));
                mon.powers.clear();
                int newamt = 0;
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(mon, mon, new PoisonPower(mon, mon, newamt)));
            }
        }

        //AbstractDungeon.actionManager.addToTurnStart(new ApplyPowerAction(p, p, new IllyaZweiDebuffPower(p, p, 1)));
        //AbstractDungeon.player.decreaseMaxOrbSlots(p.maxOrbs);
    }

    @Override
    public void atStartOfTurn() {
        //AbstractDungeon.player.gainEnergy(3);
        //AbstractDungeon.actionManager.addToTop(new GainEnergyAction(3));
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    //MAKE A SECOND POWER THAT DOES ALL OF THE ZWEI SHIT
    /*
    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DiscardSpecificCardAction(card));
    }*/

    @Override
    public AbstractPower makeCopy() {
        return new IllyaZweiPower(owner, source, amount);
    }
}
