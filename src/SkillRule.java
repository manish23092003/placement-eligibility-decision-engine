public class SkillRule extends EligibilityRule {
    @Override
    public String getRuleName() {
        return "Skills";
    }

    @Override
    public boolean check(Candidate candidate, Job job) {
        String[] requiredSkills = job.getRequiredSkills();

        for (int i = 0; i < requiredSkills.length; i++) {
            if (!candidate.hasSkill(requiredSkills[i])) {
                return false;
            }
        }

        return true;
    }
}