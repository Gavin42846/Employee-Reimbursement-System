export interface ReimbursementWithUser {
    reimbId: number;
    userId: number;
    username: string;
    description: string;
    amount: number;
    status: string;
}