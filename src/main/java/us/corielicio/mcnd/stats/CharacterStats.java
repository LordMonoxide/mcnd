package us.corielicio.mcnd.stats;

import java.util.EnumMap;
import java.util.Map;

public class CharacterStats {
  private int proficiency;

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

  public int proficiency() {
    return this.proficiency;
  }

  public int passivePerception() {
    return 10 + this.stat(Stats.WIS).bonus();
  }

  void setProficiency(final int proficiency) {
    this.proficiency = proficiency;
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
      return this.bonus() + CharacterStats.this.proficiency * this.saveProficiency;
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
      return this.stat.bonus() + CharacterStats.this.proficiency * this.proficiency;
    }

    public int proficiency() {
      return this.proficiency;
    }

    void setProficiency(final int proficiency) {
      this.proficiency = proficiency;
    }
  }
}
