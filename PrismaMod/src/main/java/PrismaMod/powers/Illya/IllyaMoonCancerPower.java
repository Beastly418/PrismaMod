package PrismaMod.powers.Illya;

import PrismaMod.DefaultMod;
import PrismaMod.cards.IllyaCards.IllyaInstallCards.IllyaInstallObj;
import PrismaMod.powers.AbstractInstallPower;
import PrismaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.DecreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FocusPower;

public class IllyaMoonCancerPower extends AbstractInstallPower {
    //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction((AbstractDungeon.player, AbstractDungeon.player, new DiscoveryAction()));
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("IllyaMoonCancerPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static IllyaInstallObj install;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("PrismaModResources/images/powers/MoonCancer_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("PrismaModResources/images/powers/MoonCancer_power32.png");

    public IllyaMoonCancerPower(final AbstractCreature owner, final AbstractCreature source, final int amount, IllyaInstallObj install) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.install = install;

        type = PowerType.BUFF;
        isTurnBased = false;

        AbstractDungeon.player.increaseMaxOrbSlots(1, true);

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    @Override
    public AbstractPower makeCopy() {
        return new IllyaAvengerPower(owner, source, amount, install);
    }


    @Override
    public void atStartOfTurn() {
        if(owner.hasPower(FocusPower.POWER_ID) && owner.getPower(FocusPower.POWER_ID).amount > 1) {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, FocusPower.POWER_ID, 1));
        }
        //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(owner, source, new FocusPower(owner, 1)));
        AbstractCard c = AbstractDungeon.returnTrulyRandomColorlessCardInCombat();
        AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(c));
    }

    @Override
    public void onRemove() {
        if (this.owner.hasPower(FocusPower.POWER_ID))
            AbstractDungeon.actionManager.addToTop(new ReducePowerAction(this.owner, this.owner, FocusPower.POWER_ID, 1));
        AbstractDungeon.actionManager.addToTop(new DecreaseMaxOrbAction(1));
    }
}
