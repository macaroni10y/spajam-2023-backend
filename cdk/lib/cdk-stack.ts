import * as cdk from 'aws-cdk-lib';
import {Construct} from 'constructs';
import {ApplicationLoadBalancedFargateService} from "aws-cdk-lib/aws-ecs-patterns";
import {ContainerImage, LogDriver} from "aws-cdk-lib/aws-ecs";
import {Repository} from "aws-cdk-lib/aws-ecr";
import {AttributeType, Table} from "aws-cdk-lib/aws-dynamodb";
import {LogGroup} from "aws-cdk-lib/aws-logs";


export class CdkStack extends cdk.Stack {
    constructor(scope: Construct, id: string, props?: cdk.StackProps) {
        super(scope, id, props);

        const repository = new Repository(this, 'Repository', {
            repositoryName: 'spajam-2023-api',
        });

        const fargateService = new ApplicationLoadBalancedFargateService(this, 'FargateService', {
            taskImageOptions: {
                image: ContainerImage.fromEcrRepository(repository, ),
                logDriver: LogDriver.awsLogs({
                    logGroup: new LogGroup(this, 'AiIllustrationEcsLogGroup', {
                        logGroupName: `/aws/ecs/logs/spajam-2023-api`,
                    }),
                    streamPrefix: `ecs`,
                }),
                containerPort: 8080,
            },
        });
        fargateService.targetGroup.configureHealthCheck({
            path: '/health',
            port: '8080',
        });

        const dynamoDbTable = new Table(this, 'AiIllustrationDynamoDb', {
            tableName: 'Conversation',
            partitionKey: {
                name: 'sealId',
                type: AttributeType.STRING,
            },
        });

        dynamoDbTable.grantFullAccess(fargateService.taskDefinition.taskRole);

    }
}
