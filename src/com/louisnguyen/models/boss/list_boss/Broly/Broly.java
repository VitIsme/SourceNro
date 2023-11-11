package com.louisnguyen.models.boss.list_boss.Broly;

import com.louisnguyen.models.boss.Boss;
import com.louisnguyen.models.boss.BossID;
import com.louisnguyen.models.boss.BossStatus;
import com.louisnguyen.models.boss.BossesData;
import com.louisnguyen.models.map.ItemMap;
import com.louisnguyen.models.player.Player;
import com.louisnguyen.models.skill.Skill;
import com.louisnguyen.services.EffectSkillService;
import com.louisnguyen.services.PetService;
import com.louisnguyen.services.Service;
import com.louisnguyen.services.TaskService;
import com.louisnguyen.utils.Util;
import java.util.Random;

/**
 *
 * @Stole By Louis Nguyễn
 */
public class Broly extends Boss {

    public Broly() throws Exception {
        super(BossID.SUPER_BROLY, BossesData.SUPER_BROLY);
    }

    @Override
    public void active() {
        super.active(); //To change body of generated methods, choose Tools | Templates.
        this.SendLaiThongBao(7);
    }

    @Override
    public void joinMap() {
        super.joinMap(); //To change body of generated methods, choose Tools | Templates.
        st = System.currentTimeMillis();
    }
    private long st;

    @Override
    public void reward(Player plKill) {
                Service.gI().dropItemMap(this.zone, Util.ratiItem(zone, 568, 1, this.location.x, this.location.y, plKill.id));
                return;    
    }

    @Override
    public int injured(Player plAtt, int damage, boolean piercing, boolean isMobAttack) {
        if (!this.isDie()) {
            if (!piercing && Util.isTrue(this.nPoint.tlNeDon, 1)) {
                this.chat("Xí hụt");
                return 0;
            }
            damage = this.nPoint.subDameInjureWithDeff(damage);
            if (!piercing && effectSkill.isShielding) {
                if (damage > nPoint.hpMax) {
                    EffectSkillService.gI().breakShield(this);
                }
                damage = 1;
            }
            this.nPoint.subHP(damage);
            if (isDie()) {
                this.setDie(plAtt);
                die(plAtt);
            }
            return damage;
        } else {
            return 0;
        }
    }
}
