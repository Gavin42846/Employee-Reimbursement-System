export interface Reimbursement {
    userId: number;
    description: string;
    amount: number;
    status: string;
    user: {  
        userId: number;
        username: string;
    };
}