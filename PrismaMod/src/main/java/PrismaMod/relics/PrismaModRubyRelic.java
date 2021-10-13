
package PrismaMod.relics;

import PrismaMod.DefaultMod;
import PrismaMod.characters.Illya;
import PrismaMod.orbs.*;
import PrismaMod.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static PrismaMod.DefaultMod.makeRelicOutlinePath;
import static PrismaMod.DefaultMod.makeRelicPath;

public class PrismaModRubyRelic extends CustomRelic {

    public static final String ID = DefaultMod.makeID("PrismaRuby");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("Ruby_Relic.png"));

    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("placeholder_relic.png"));

    public PrismaModRubyRelic() {
        super(ID, IMG, OUTLINE, RelicTier.STARTER, LandingSound.MAGICAL);
    }


    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0]; // DESCRIPTIONS pulls from your localization file
    }


    @Override
    public void atBattleStart() {
        double rand = Math.random();
        if (rand < .33) {
            AbstractDungeon.actionManager.addToTop(new ChannelAction(new IllyaAttackOrb()));
        } else if (rand >= .33 && rand < .66) {
            AbstractDungeon.actionManager.addToTop(new ChannelAction(new IllyaDefendOrb()));
        } else {
            AbstractDungeon.actionManager.addToTop(new ChannelAction(new IllyaPoisonOrb()));
        }
    }

}
