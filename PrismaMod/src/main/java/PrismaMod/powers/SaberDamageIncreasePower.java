package PrismaMod.powers;

import PrismaMod.DefaultMod;
import PrismaMod.cards.AbstractDynamicCard;
import PrismaMod.cards.IllyaCards.IllyaInstallCards.IllyaInstallObj;
import PrismaMod.util.TextureLoader;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.unique.DiscoveryAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.QuickSlash;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.Iterator;

import static com.megacrit.cardcrawl.monsters.AbstractMonster.EnemyType.BOSS;

public class SaberDamageIncreasePower extends AbstractInstallPower  {
    //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction((AbstractDungeon.player, AbstractDungeon.player, new DiscoveryAction()));
    //APPLY THESE TO ALL OF THE ENEMIES
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("SaberDamageIncreasePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("PrismaModResources/images/powers/Saber_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("PrismaModResources/images/powers/Saber_power32.png");

    public SaberDamageIncreasePower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
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
        description = DESCRIPTIONS[0] + "50" + DESCRIPTIONS[1];
    }

    @Override
    public AbstractPower makeCopy() {
        return new SaberDamageIncreasePower(owner, source, amount);
    }

    @Override
    public float atDamageFinalGive(float damage, DamageInfo.DamageType type) {
        return damage*1.5f;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }



}

