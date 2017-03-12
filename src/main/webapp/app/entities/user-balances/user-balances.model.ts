import { User } from '../../shared';
import { Category } from '../category';
import { User_tweet_log } from '../user-tweet-log';
export class User_balances {
    constructor(
        public id?: number,
        public balance?: number,
        public user?: User,
        public category?: Category,
        public user_tweet_log?: User_tweet_log,
    ) { }
}
