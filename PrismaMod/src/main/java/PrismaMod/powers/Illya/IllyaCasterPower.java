package PrismaMod.powers.Illya;

import PrismaMod.DefaultMod;
import PrismaMod.cards.IllyaCards.IllyaInstallCards.IllyaInstallObj;
import PrismaMod.orbs.IllyaAttackOrb;
import PrismaMod.orbs.IllyaDefendOrb;
import PrismaMod.orbs.IllyaPoisonOrb;
import PrismaMod.powers.AbstractInstallPower;
import PrismaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.DecreaseMaxOrbAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.powers.FocusPower;

public class IllyaCasterPower extends AbstractInstallPower {
    //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction((AbstractDungeon.player, AbstractDungeon.player, new DiscoveryAction()));
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("IllyaCasterPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static IllyaInstallObj install;

    public int ENERGY_INC;
    public int ORB_INC;
    public int EVOKE_COST_DEC;
    public boolean GAIN_ORB;
    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("PrismaModResources/images/powers/Caster_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("PrismaModResources/images/powers/Caster_power32.png");

    public IllyaCasterPower(final AbstractCreature owner, final AbstractCreature source, final int amount, IllyaInstallObj install) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.install = install;

        ENERGY_INC = this.install.getEnergyInc();
        ORB_INC = this.install.getNumOrbsInc();
        EVOKE_COST_DEC = this.install.getEvokeCostDec();
        GAIN_ORB = this.install.isGainRandomOrb();

        if (GAIN_ORB) {
            AbstractDungeon.player.increaseMaxOrbSlots(ORB_INC, true);
        }
        type = PowerType.BUFF;
        isTurnBased = false;


        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        if(DefaultMod.getLanguageString(Settings.language).equalsIgnoreCase("zhs")) {
            description = DESCRIPTIONS[0] + ENERGY_INC + DESCRIPTIONS[1] + GAIN_ORB + DESCRIPTIONS[2];
        } else if (DefaultMod.getLanguageString(Settings.language).equalsIgnoreCase("kor")){
            description = DESCRIPTIONS[0] + ENERGY_INC + DESCRIPTIONS[1];
        } else {
            description = DESCRIPTIONS[0] + ENERGY_INC + DESCRIPTIONS[1] + ORB_INC + DESCRIPTIONS[2] + 1 + DESCRIPTIONS[3];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new IllyaCasterPower(owner, source, amount, install);
    }

    /*@Override
    public void onCardDraw(AbstractCard card) {
        if (card.hasTag(IllyaCardTags.EVOKE_CARD) || card.showEvokeValue) {
            card.modifyCostForTurn(-1);
        }
    }*/

    @Override
    public void onVictory() {

    }

    @Override
    public void atStartOfTurn() {
        //AbstractDungeon.player.gainEnergy(install.getNumEnergy());
        //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(owner, source, new FocusPower(owner, 1)));
        if(owner.hasPower(FocusPower.POWER_ID) && owner.getPower(FocusPower.POWER_ID).amount > 1) {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, FocusPower.POWER_ID, 1));
        }
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, source, new EnergizedPower(owner, ENERGY_INC)));
        int rand = (int) (Math.random()*3);
        if (rand == 0) {
            AbstractDungeon.actionManager.addToTop(new ChannelAction(new IllyaAttackOrb(), true));
        } else if (rand == 1) {
            AbstractDungeon.actionManager.addToTop(new ChannelAction(new IllyaDefendOrb(), true));
        } else if (rand == 2) {
            AbstractDungeon.actionManager.addToTop(new ChannelAction(new IllyaPoisonOrb(), true));
        }
    }

    @Override
    public void onRemove() {
        if (this.owner.hasPower(FocusPower.POWER_ID))
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, FocusPower.POWER_ID, 1));
        AbstractDungeon.actionManager.addToTop(new DecreaseMaxOrbAction(1));
    }
}
