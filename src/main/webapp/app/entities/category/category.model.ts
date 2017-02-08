import { Charity } from '../charity';
import { Tweetlog } from '../tweetlog';
export class Category {
    constructor(
        public id?: number,
        public name?: string,
        public charity?: Charity,
        public tweetlog?: Tweetlog,
    ) { }
}
