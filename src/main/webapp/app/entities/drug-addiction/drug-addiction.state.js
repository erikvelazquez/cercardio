(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('drug-addiction', {
            parent: 'entity',
            url: '/drug-addiction',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.drugAddiction.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/drug-addiction/drug-addictions.html',
                    controller: 'DrugAddictionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('drugAddiction');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('drug-addiction-detail', {
            parent: 'drug-addiction',
            url: '/drug-addiction/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.drugAddiction.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/drug-addiction/drug-addiction-detail.html',
                    controller: 'DrugAddictionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('drugAddiction');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'DrugAddiction', function($stateParams, DrugAddiction) {
                    return DrugAddiction.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'drug-addiction',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('drug-addiction-detail.edit', {
            parent: 'drug-addiction-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/drug-addiction/drug-addiction-dialog.html',
                    controller: 'DrugAddictionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DrugAddiction', function(DrugAddiction) {
                            return DrugAddiction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('drug-addiction.new', {
            parent: 'drug-addiction',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/drug-addiction/drug-addiction-dialog.html',
                    controller: 'DrugAddictionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                isactive: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('drug-addiction', null, { reload: 'drug-addiction' });
                }, function() {
                    $state.go('drug-addiction');
                });
            }]
        })
        .state('drug-addiction.edit', {
            parent: 'drug-addiction',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/drug-addiction/drug-addiction-dialog.html',
                    controller: 'DrugAddictionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['DrugAddiction', function(DrugAddiction) {
                            return DrugAddiction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('drug-addiction', null, { reload: 'drug-addiction' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('drug-addiction.delete', {
            parent: 'drug-addiction',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/drug-addiction/drug-addiction-delete-dialog.html',
                    controller: 'DrugAddictionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['DrugAddiction', function(DrugAddiction) {
                            return DrugAddiction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('drug-addiction', null, { reload: 'drug-addiction' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
