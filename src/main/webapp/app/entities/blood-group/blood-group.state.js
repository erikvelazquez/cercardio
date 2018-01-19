(function() {
    'use strict';

    angular
        .module('cercardiobitiApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('blood-group', {
            parent: 'entity',
            url: '/blood-group',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.bloodGroup.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/blood-group/blood-groups.html',
                    controller: 'BloodGroupController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bloodGroup');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('blood-group-detail', {
            parent: 'blood-group',
            url: '/blood-group/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'cercardiobitiApp.bloodGroup.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/blood-group/blood-group-detail.html',
                    controller: 'BloodGroupDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('bloodGroup');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'BloodGroup', function($stateParams, BloodGroup) {
                    return BloodGroup.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'blood-group',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('blood-group-detail.edit', {
            parent: 'blood-group-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/blood-group/blood-group-dialog.html',
                    controller: 'BloodGroupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BloodGroup', function(BloodGroup) {
                            return BloodGroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('blood-group.new', {
            parent: 'blood-group',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/blood-group/blood-group-dialog.html',
                    controller: 'BloodGroupDialogController',
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
                    $state.go('blood-group', null, { reload: 'blood-group' });
                }, function() {
                    $state.go('blood-group');
                });
            }]
        })
        .state('blood-group.edit', {
            parent: 'blood-group',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/blood-group/blood-group-dialog.html',
                    controller: 'BloodGroupDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['BloodGroup', function(BloodGroup) {
                            return BloodGroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('blood-group', null, { reload: 'blood-group' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('blood-group.delete', {
            parent: 'blood-group',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/blood-group/blood-group-delete-dialog.html',
                    controller: 'BloodGroupDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['BloodGroup', function(BloodGroup) {
                            return BloodGroup.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('blood-group', null, { reload: 'blood-group' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
