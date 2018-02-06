(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ethnic-group', {
            parent: 'entity',
            url: '/ethnic-group',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.ethnicGroup.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ethnic-group/ethnic-groups.html',
                    controller: 'EthnicGroupController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ethnicGroup');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('ethnic-group-detail', {
            parent: 'ethnic-group',
            url: '/ethnic-group/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.ethnicGroup.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ethnic-group/ethnic-group-detail.html',
                    controller: 'EthnicGroupDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ethnicGroup');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'EthnicGroup', function($stateParams, EthnicGroup) {
                    return EthnicGroup.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ethnic-group',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ethnic-group-detail.edit', {
            parent: 'ethnic-group-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ethnic-group/ethnic-group-dialog.html',
                    controller: 'EthnicGroupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EthnicGroup', function(EthnicGroup) {
                            return EthnicGroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ethnic-group.new', {
            parent: 'ethnic-group',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ethnic-group/ethnic-group-dialog.html',
                    controller: 'EthnicGroupDialogController',
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
                    $state.go('ethnic-group', null, { reload: 'ethnic-group' });
                }, function() {
                    $state.go('ethnic-group');
                });
            }]
        })
        .state('ethnic-group.edit', {
            parent: 'ethnic-group',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ethnic-group/ethnic-group-dialog.html',
                    controller: 'EthnicGroupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['EthnicGroup', function(EthnicGroup) {
                            return EthnicGroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ethnic-group', null, { reload: 'ethnic-group' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ethnic-group.delete', {
            parent: 'ethnic-group',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ethnic-group/ethnic-group-delete-dialog.html',
                    controller: 'EthnicGroupDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['EthnicGroup', function(EthnicGroup) {
                            return EthnicGroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ethnic-group', null, { reload: 'ethnic-group' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
