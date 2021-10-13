package PrismaMod.powers.Illya;

import PrismaMod.DefaultMod;
import PrismaMod.cards.IllyaCards.IllyaInstallCards.IllyaInstallObj;
import PrismaMod.powers.AbstractInstallPower;
import PrismaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.defect.DecreaseMaxOrbAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class IllyaAlterEgoPower extends AbstractInstallPower {
    //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction((AbstractDungeon.player, AbstractDungeon.player, new DiscoveryAction()));
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("IllyaAlterEgoPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static IllyaInstallObj install;
    public int NUM_ORBS_INC;
    public int NUM_ORBS_EVOKE;
    public int newCost;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("PrismaModResources/images/powers/AlterEgo_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("PrismaModResources/images/powers/AlterEgo_power32.png");

    public IllyaAlterEgoPower(final AbstractCreature owner, final AbstractCreature source, final int amount, IllyaInstallObj install) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.install = install;
        NUM_ORBS_INC = this.install.getNumOrbsInc();
        NUM_ORBS_EVOKE = this.install.getEvokeNumOrbs();
        this.newCost = this.install.getSkillCostDec();


        type = PowerType.BUFF;
        isTurnBased = true;

        AbstractDungeon.player.increaseMaxOrbSlots(1, true);


        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (DefaultMod.getLanguageString(Settings.language).equalsIgnoreCase("zhs")){
            description = DESCRIPTIONS[0] + NUM_ORBS_INC + DESCRIPTIONS[1] + newCost + DESCRIPTIONS[2];
        } else {
            description = DESCRIPTIONS[0] + NUM_ORBS_INC + DESCRIPTIONS[1] + NUM_ORBS_EVOKE + DESCRIPTIONS[2] + newCost + DESCRIPTIONS[3];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new IllyaAvengerPower(owner, source, amount, install);
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        return damageAmount/2;
    }

    //So when you draw a Skill/Attack it will modify its cost to 1 less
    @Override
    public void onCardDraw(AbstractCard card) {
        if (card.type == AbstractCard.CardType.SKILL && card.cost > 0) {
            card.setCostForTurn(card.cost-1);
        }
    }

    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToTop(new EvokeOrbAction(NUM_ORBS_EVOKE));

    }

    @Override
    public void onRemove() {
        //AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, FocusPower.POWER_ID));
        AbstractDungeon.actionManager.addToTop(new DecreaseMaxOrbAction(1));
    }
}
