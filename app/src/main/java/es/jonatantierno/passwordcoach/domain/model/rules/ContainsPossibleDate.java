package es.jonatantierno.passwordcoach.domain.model.rules;

public class ContainsPossibleDate extends WeakPasswordResult{

    public ContainsPossibleDate(){
        super(ResultCode.CONTAINS_DATE);
    }
}
