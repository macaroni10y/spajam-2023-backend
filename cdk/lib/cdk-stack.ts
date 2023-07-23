import * as cdk from 'aws-cdk-lib';
import {RemovalPolicy} from 'aws-cdk-lib';
import {Construct} from 'constructs';
import {Repository} from "aws-cdk-lib/aws-ecr";
import {AttributeType, Table} from "aws-cdk-lib/aws-dynamodb";
import {Service, Source} from '@aws-cdk/aws-apprunner-alpha';
import {Role, ServicePrincipal} from "aws-cdk-lib/aws-iam";

export class CdkStack extends cdk.Stack {
    constructor(scope: Construct, id: string, props?: cdk.StackProps) {
        super(scope, id, props);

        const repository = new Repository(this, 'Repository', {
            repositoryName: 'spajam-2023-api',
        });

        const appRunnerInstanceRole = new Role(this, 'InstanceRole', {
            assumedBy: new ServicePrincipal('tasks.apprunner.amazonaws.com')
        });
        const appRunnerService = new Service(this, 'Service', {
            autoDeploymentsEnabled: true,
            instanceRole: appRunnerInstanceRole,
            source: Source.fromEcr({
                repository: repository,
                tagOrDigest: 'latest',
            }),
        });

        // const fargateService = new ApplicationLoadBalancedFargateService(this, 'FargateService', {
        //     taskImageOptions: {
        //         image: ContainerImage.fromEcrRepository(repository),
        //         logDriver: LogDriver.awsLogs({
        //             logGroup: new LogGroup(this, 'EcsLogGroup', {
        //                 logGroupName: `/aws/ecs/logs/spajam-2023-api`,
        //             }),
        //             streamPrefix: `ecs`,
        //         }),
        //         containerPort: 8080,
        //     },
        // });
        // fargateService.targetGroup.configureHealthCheck({
        //     path: '/health',
        //     port: '8080',
        // });

        const dynamoDbTable = new Table(this, 'DynamoDb', {
            tableName: 'Conversation',
            partitionKey: {
                name: 'conversationId',
                type: AttributeType.STRING,
            },
            removalPolicy: RemovalPolicy.DESTROY,
        });

        // dynamoDbTable.grantFullAccess(fargateService.taskDefinition.taskRole);
        dynamoDbTable.grantFullAccess(appRunnerInstanceRole);
    }
}
