(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('private-health-insurance', {
            parent: 'entity',
            url: '/private-health-insurance',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.privateHealthInsurance.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/private-health-insurance/private-health-insurances.html',
                    controller: 'PrivateHealthInsuranceController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('privateHealthInsurance');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('private-health-insurance-detail', {
            parent: 'private-health-insurance',
            url: '/private-health-insurance/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.privateHealthInsurance.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/private-health-insurance/private-health-insurance-detail.html',
                    controller: 'PrivateHealthInsuranceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('privateHealthInsurance');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PrivateHealthInsurance', function($stateParams, PrivateHealthInsurance) {
                    return PrivateHealthInsurance.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'private-health-insurance',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('private-health-insurance-detail.edit', {
            parent: 'private-health-insurance-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/private-health-insurance/private-health-insurance-dialog.html',
                    controller: 'PrivateHealthInsuranceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PrivateHealthInsurance', function(PrivateHealthInsurance) {
                            return PrivateHealthInsurance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('private-health-insurance.new', {
            parent: 'private-health-insurance',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/private-health-insurance/private-health-insurance-dialog.html',
                    controller: 'PrivateHealthInsuranceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                isactive: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('private-health-insurance', null, { reload: 'private-health-insurance' });
                }, function() {
                    $state.go('private-health-insurance');
                });
            }]
        })
        .state('private-health-insurance.edit', {
            parent: 'private-health-insurance',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/private-health-insurance/private-health-insurance-dialog.html',
                    controller: 'PrivateHealthInsuranceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PrivateHealthInsurance', function(PrivateHealthInsurance) {
                            return PrivateHealthInsurance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('private-health-insurance', null, { reload: 'private-health-insurance' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('private-health-insurance.delete', {
            parent: 'private-health-insurance',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/private-health-insurance/private-health-insurance-delete-dialog.html',
                    controller: 'PrivateHealthInsuranceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PrivateHealthInsurance', function(PrivateHealthInsurance) {
                            return PrivateHealthInsurance.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('private-health-insurance', null, { reload: 'private-health-insurance' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
