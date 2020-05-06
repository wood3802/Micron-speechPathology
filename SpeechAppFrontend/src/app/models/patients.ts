export class Patient {
    birthday: string;
    sex: string;
    race: string;
    nationality: string;
    speech_ability: string;
    diagnosis: string;
    therapistfname: string;
    therapistlname: string;

    constructor(values: Object = {}) {
        Object.assign(this, values);
    }
}