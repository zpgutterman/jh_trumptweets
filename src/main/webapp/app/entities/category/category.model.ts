import { Charity } from '../charity';
import { Tweetlog } from '../tweetlog';
import { User_preferences } from '../user-preferences';
export class Category {
    constructor(
        public id?: number,
        public name?: string,
        public charity?: Charity,
        public tweetlog?: Tweetlog,
        public user_exclusions?: User_preferences,
    ) { }
}
