(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('timers', {
            parent: 'entity',
            url: '/timers',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.timers.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/timers/timers.html',
                    controller: 'TimersController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('timers');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('timers-detail', {
            parent: 'timers',
            url: '/timers/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.timers.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/timers/timers-detail.html',
                    controller: 'TimersDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('timers');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Timers', function($stateParams, Timers) {
                    return Timers.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'timers',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('timers-detail.edit', {
            parent: 'timers-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/timers/timers-dialog.html',
                    controller: 'TimersDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Timers', function(Timers) {
                            return Timers.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('timers.new', {
            parent: 'timers',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/timers/timers-dialog.html',
                    controller: 'TimersDialogController',
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
                    $state.go('timers', null, { reload: 'timers' });
                }, function() {
                    $state.go('timers');
                });
            }]
        })
        .state('timers.edit', {
            parent: 'timers',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/timers/timers-dialog.html',
                    controller: 'TimersDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Timers', function(Timers) {
                            return Timers.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('timers', null, { reload: 'timers' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('timers.delete', {
            parent: 'timers',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/timers/timers-delete-dialog.html',
                    controller: 'TimersDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Timers', function(Timers) {
                            return Timers.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('timers', null, { reload: 'timers' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
