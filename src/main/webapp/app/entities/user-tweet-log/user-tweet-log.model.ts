import { User } from '../../shared';
import { Tweetlog } from '../tweetlog';
import { User_balances } from '../user-balances';
export class User_tweet_log {
    constructor(
        public id?: number,
        public charge?: number,
        public user?: User,
        public tweet?: Tweetlog,
        public user_balances?: User_balances,
    ) { }
}
