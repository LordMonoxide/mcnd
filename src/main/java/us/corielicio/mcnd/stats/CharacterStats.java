package us.corielicio.mcnd.stats;

import java.util.EnumMap;
import java.util.Map;

public class CharacterStats {
  private int level = 1;
  private int exp;

  private final Map<Stats, Stat> stats = new EnumMap<>(Stats.class);
  private final Map<Skills, Skill> skills = new EnumMap<>(Skills.class);

  public CharacterStats() {
    for(final Stats stat : Stats.values()) {
      this.stats.put(stat, new Stat());
    }

    for(final Skills skill : Skills.values()) {
      this.skills.put(skill, new Skill(this.stat(skill.stat)));
    }
  }

  public Stat stat(final Stats stat) {
    return this.stats.get(stat);
  }

  public Skill skill(final Skills skill) {
    return this.skills.get(skill);
  }

  public int level() {
    return Math.min(Math.max(1, this.level), 20);
  }

  public int exp() {
    return this.exp;
  }

  public int nextLevelExp() {
    switch(this.level) {
      case  1: return    300;
      case  2: return    900;
      case  3: return   2700;
      case  4: return   6500;
      case  5: return  14000;
      case  6: return  23000;
      case  7: return  34000;
      case  8: return  48000;
      case  9: return  64000;
      case 10: return  85000;
      case 11: return 100000;
      case 12: return 120000;
      case 13: return 140000;
      case 14: return 165000;
      case 15: return 195000;
      case 16: return 225000;
      case 17: return 265000;
      case 18: return 305000;
      case 19: return 335000;
      default: return Integer.MAX_VALUE;
    }
  }

  public int proficiency() {
    return (int)Math.ceil(this.level / 4) + 1;
  }

  public int passivePerception() {
    return 10 + this.stat(Stats.WIS).bonus();
  }

  void setLevel(final int level) {
    this.level = level;
  }

  void setExp(final int exp) {
    this.exp = exp;
  }

  public class Stat {
    private int stat;
    private int saveProficiency;

    public int raw() {
      return this.stat;
    }

    public int total() {
      return this.raw();
    }

    public int bonus() {
      return Math.floorDiv(this.total() - 10, 2);
    }

    public int save() {
      return this.bonus() + CharacterStats.this.proficiency() * this.saveProficiency;
    }

    public int saveProficiency() {
      return this.saveProficiency;
    }

    void set(final int stat) {
      this.stat = stat;
    }

    void setSaveProficiency(final int saveProficiency) {
      this.saveProficiency = saveProficiency;
    }
  }
  public class Skill {
    private final Stat stat;
    private int proficiency;

    public Skill(final Stat stat) {
      this.stat = stat;
    }

    public int total() {
      return this.stat.bonus() + CharacterStats.this.proficiency() * this.proficiency;
    }

    public int proficiency() {
      return this.proficiency;
    }

    void setProficiency(final int proficiency) {
      this.proficiency = proficiency;
    }
  }
}
