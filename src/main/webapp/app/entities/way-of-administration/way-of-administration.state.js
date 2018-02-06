(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('way-of-administration', {
            parent: 'entity',
            url: '/way-of-administration',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.way_of_Administration.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/way-of-administration/way-of-administrations.html',
                    controller: 'Way_of_AdministrationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('way_of_Administration');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('way-of-administration-detail', {
            parent: 'way-of-administration',
            url: '/way-of-administration/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.way_of_Administration.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/way-of-administration/way-of-administration-detail.html',
                    controller: 'Way_of_AdministrationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('way_of_Administration');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Way_of_Administration', function($stateParams, Way_of_Administration) {
                    return Way_of_Administration.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'way-of-administration',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('way-of-administration-detail.edit', {
            parent: 'way-of-administration-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/way-of-administration/way-of-administration-dialog.html',
                    controller: 'Way_of_AdministrationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Way_of_Administration', function(Way_of_Administration) {
                            return Way_of_Administration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('way-of-administration.new', {
            parent: 'way-of-administration',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/way-of-administration/way-of-administration-dialog.html',
                    controller: 'Way_of_AdministrationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                wayofadministration: null,
                                isactive: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('way-of-administration', null, { reload: 'way-of-administration' });
                }, function() {
                    $state.go('way-of-administration');
                });
            }]
        })
        .state('way-of-administration.edit', {
            parent: 'way-of-administration',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/way-of-administration/way-of-administration-dialog.html',
                    controller: 'Way_of_AdministrationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Way_of_Administration', function(Way_of_Administration) {
                            return Way_of_Administration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('way-of-administration', null, { reload: 'way-of-administration' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('way-of-administration.delete', {
            parent: 'way-of-administration',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/way-of-administration/way-of-administration-delete-dialog.html',
                    controller: 'Way_of_AdministrationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Way_of_Administration', function(Way_of_Administration) {
                            return Way_of_Administration.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('way-of-administration', null, { reload: 'way-of-administration' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
