package PrismaMod.powers.Illya;

import PrismaMod.DefaultMod;
import PrismaMod.cards.IllyaCards.IllyaInstallCards.IllyaInstallObj;
import PrismaMod.powers.AbstractInstallPower;
import PrismaMod.util.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.*;

public class IllyaLancerPower extends AbstractInstallPower {
    //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction((AbstractDungeon.player, AbstractDungeon.player, new DiscoveryAction()));
    //APPLY THESE TO ALL OF THE ENEMIES
    public AbstractCreature source;

    public static final String POWER_ID = DefaultMod.makeID("IllyaLancerPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static IllyaInstallObj install;

    public int NUM_FRAIL;
    public int NUM_VUL;
    public int NUM_STR;
    public int NUM_WEAK;

    private boolean applyStacks;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("PrismaModResources/images/powers/Lancer_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("PrismaModResources/images/powers/Lancer_power32.png");

    public IllyaLancerPower(final AbstractCreature owner, final AbstractCreature source, final int amount, IllyaInstallObj install){
        this(owner, source, amount, install, false);
    }

    public IllyaLancerPower(final AbstractCreature owner, final AbstractCreature source, final int amount, IllyaInstallObj install, boolean applyStacks) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;
        this.install = install;

        NUM_FRAIL = this.install.getNumApplyFrail();
        NUM_VUL = this.install.getNumApplyVulnerable();
        NUM_STR = this.install.getNumApplyStrength();
        NUM_WEAK = this.install.getNumApplyWeak();
        type = PowerType.BUFF;
        isTurnBased = false;

        this.applyStacks = applyStacks;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (DefaultMod.getLanguageString(Settings.language).equalsIgnoreCase("zhs")) {
            description = DESCRIPTIONS[0] + "2" + DESCRIPTIONS[1] + NUM_VUL + DESCRIPTIONS[2];
        } else if (DefaultMod.getLanguageString(Settings.language).equalsIgnoreCase("kor")) {
            description = DESCRIPTIONS[0] + "2" + DESCRIPTIONS[1] + NUM_VUL + DESCRIPTIONS[2];//Korean
        } else {
            description = DESCRIPTIONS[0] + NUM_VUL + DESCRIPTIONS[1] + "2" + DESCRIPTIONS[2];//Default to English
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new IllyaLancerPower(owner, source, amount, install, applyStacks);
    }

    /*
    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature m) {
        if(this.applyStacks){
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, m, new WeakPower(m, NUM_WEAK, true)));
            //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, target, new StrengthPower(target, NUM_STR)));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, m, new FrailPower(m, NUM_FRAIL, true)));
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, m, new VulnerablePower(m, NUM_VUL, true)));
            this.applyStacks = false;
        } else {
            this.applyStacks = true;
        }
    }*/


    @Override
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            if(this.applyStacks){
                if(m == null){
                    MonsterGroup mon = AbstractDungeon.getMonsters();
                    for(AbstractMonster monst : mon.monsters){
                        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(monst, monst, new WeakPower(monst, NUM_WEAK, true)));
                        //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, target, new StrengthPower(target, NUM_STR)));
                        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(monst, monst, new FrailPower(monst, NUM_FRAIL, true)));
                        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(monst, monst, new VulnerablePower(monst, NUM_VUL, true)));
                    }
                    this.applyStacks = false;
                } else {
                    AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, m, new WeakPower(m, NUM_WEAK, true)));
                    //AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(target, target, new StrengthPower(target, NUM_STR)));
                    AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, m, new FrailPower(m, NUM_FRAIL, true)));
                    AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, m, new VulnerablePower(m, NUM_VUL, true)));
                    this.applyStacks = false;
                }
            } else {
                this.applyStacks = true;
            }
        }
    }



}

