(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('civil-status', {
            parent: 'entity',
            url: '/civil-status',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.civilStatus.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/civil-status/civil-statuses.html',
                    controller: 'CivilStatusController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('civilStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('civil-status-detail', {
            parent: 'civil-status',
            url: '/civil-status/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.civilStatus.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/civil-status/civil-status-detail.html',
                    controller: 'CivilStatusDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('civilStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CivilStatus', function($stateParams, CivilStatus) {
                    return CivilStatus.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'civil-status',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('civil-status-detail.edit', {
            parent: 'civil-status-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/civil-status/civil-status-dialog.html',
                    controller: 'CivilStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CivilStatus', function(CivilStatus) {
                            return CivilStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('civil-status.new', {
            parent: 'civil-status',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/civil-status/civil-status-dialog.html',
                    controller: 'CivilStatusDialogController',
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
                    $state.go('civil-status', null, { reload: 'civil-status' });
                }, function() {
                    $state.go('civil-status');
                });
            }]
        })
        .state('civil-status.edit', {
            parent: 'civil-status',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/civil-status/civil-status-dialog.html',
                    controller: 'CivilStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CivilStatus', function(CivilStatus) {
                            return CivilStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('civil-status', null, { reload: 'civil-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('civil-status.delete', {
            parent: 'civil-status',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/civil-status/civil-status-delete-dialog.html',
                    controller: 'CivilStatusDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CivilStatus', function(CivilStatus) {
                            return CivilStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('civil-status', null, { reload: 'civil-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
