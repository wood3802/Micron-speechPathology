export class User {
    therapist_id: string;
    caregiver_id: string;
    character: string;
    username: string;
    password: string;
    birthday: string;
    sex: string;
    race: string;
    nationality: string;
    speech_ability: string;
    diagnosis: string;
    firstName: string;
    lastName: string;
    
    constructor(values: Object = {}) {
        Object.assign(this, values);
      }
}